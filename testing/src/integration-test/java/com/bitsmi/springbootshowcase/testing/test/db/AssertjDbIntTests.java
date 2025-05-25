 package com.bitsmi.springbootshowcase.testing.test.db;

import com.bitsmi.springbootshowcase.testing.db.DbPackage;
import com.bitsmi.springbootshowcase.testing.db.entity.CategoryEntity;
import com.bitsmi.springbootshowcase.testing.db.entity.ProductEntity;
import com.bitsmi.springbootshowcase.testing.db.repository.CategoryRepository;
import com.bitsmi.springbootshowcase.testing.db.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.assertj.db.type.AssertDbConnection;
import org.assertj.db.type.AssertDbConnectionFactory;
import org.assertj.db.type.Changes;
import org.assertj.db.type.DateTimeValue;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static org.assertj.db.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
// Test-managed transaction should be rolled back after the test method has completed.
@Rollback
@EnableAutoConfiguration
//In-memory database (H2). Entity auto-scan
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Import({ValidationAutoConfiguration.class})

@Tag("IntegrationTest")
@TestPropertySource(
    properties = {
            "spring.liquibase.change-log=classpath:db/changelogs/test/assertj-db_tests_main.xml",
            "spring.jpa.show-sql=true",
            "spring.jpa.properties.hibernate.format_sql=true"
    }
)
class AssertjDbIntTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private AssertDbConnection connection;

    @BeforeEach
    void setUp() {
        /* It is needed to wrap datasource to allow access to current transaction connection.
         * Otherwise, <code>AssertDbConnection</code> won't be allowed to access uncommited changes
         */
        connection = AssertDbConnectionFactory.of(new TransactionAwareDataSourceProxy(dataSource))
                .create();
    }

    @Test
    @DisplayName("Check an inserted row by its row index using row API")
    void rowApiTest1() {
        final LocalDateTime referenceDateTime = LocalDateTime.now();

        CategoryEntity categoryEntity = categoryRepository.findById(1001L)
                .orElseThrow();
        ProductEntity productEntity = ProductEntity.builder()
                .externalId("test-product")
                .name("Test product")
                .category(categoryEntity)
                .build();
        productRepository.save(productEntity);

        entityManager.flush();
        Table productTable = connection.table("PRODUCT").build();
        assertThat(productTable)
                // +1
                .hasNumberOfRows(6)
                // As inserted PK is lesser than table test data PKs, it will be the first row
                .row(0)
                    .value("ID").isNotNull()
                    .value("VERSION").isNotNull()
                    .value("EXTERNAL_ID").isEqualTo("test-product")
                    .value("NAME").isEqualTo("Test product")
                    .value("CATEGORY_ID").isEqualTo(1001L)
                    .value("CREATION_DATE").isAfter(DateTimeValue.from(referenceDateTime))
                    .value("LAST_UPDATED").isAfter(DateTimeValue.from(referenceDateTime));
    }

    @Test
    @DisplayName("Check an inserted row using change API")
    void changeApiTest1() {
        final LocalDateTime referenceDateTime = LocalDateTime.now();

        final Changes changes = connection.changes().build();
        changes.setStartPointNow();

        CategoryEntity categoryEntity = categoryRepository.findById(1001L)
                .orElseThrow();
        ProductEntity productEntity = ProductEntity.builder()
                .externalId("test-product")
                .name("Test product")
                .category(categoryEntity)
                .build();
        productRepository.save(productEntity);

        entityManager.flush();
        changes.setEndPointNow();

        assertThat(changes)
                .hasNumberOfChanges(1)
                .ofCreationOnTable("PRODUCT")
                    .hasNumberOfChanges(1)
                    .changeOfCreationOnTable("PRODUCT")
                        .rowAtEndPoint()
                            .value("ID").isNotNull()
                            .value("VERSION").isNotNull()
                            .value("EXTERNAL_ID").isEqualTo("test-product")
                            .value("NAME").isEqualTo("Test product")
                            .value("CATEGORY_ID").isEqualTo(1001L)
                            .value("CREATION_DATE").isAfter(DateTimeValue.from(referenceDateTime))
                            .value("LAST_UPDATED").isAfter(DateTimeValue.from(referenceDateTime));
    }

    @Test
    @DisplayName("Check changes using restraining changes using a request")
    void changeApiTest2() {
        final String modifiedName = "Modified category";

        final Changes changes = connection.changes()
                .request(
                        "select * from CATEGORY where ID = ?",
                        customizer -> customizer.parameters(1001L).pksName("ID")
                )
                .build();
        changes.setStartPointNow();

        CategoryEntity categoryEntity = categoryRepository.findById(1001L)
                .orElseThrow();
        categoryEntity.setName(modifiedName);
        categoryRepository.save(categoryEntity);
        ProductEntity productEntity = ProductEntity.builder()
                .externalId("test-product")
                .name("Test product")
                .category(categoryEntity)
                .build();
        productRepository.save(productEntity);

        entityManager.flush();
        changes.setEndPointNow();

        assertThat(changes)
                .hasNumberOfChanges(1)
                // No changes registered in PRODUCT as we are only observing CATEGORY
                .ofCreationOnTable("PRODUCT")
                    .hasNumberOfChanges(0)
                // Check changes on CATEGORY
                .ofModificationOnTable("CATEGORY")
                    .changeOfModification()
                        .hasModifiedColumns("NAME", "VERSION", "LAST_UPDATED")
                        .columnAmongTheModifiedOnes("NAME")
                            .valueAtEndPoint()
                            .isEqualTo(modifiedName);
    }

    @Test
    @DisplayName("Check table values using request API")
    void requestApiTest1() {
        final LocalDateTime referenceDateTime = LocalDateTime.now();

        CategoryEntity categoryEntity = categoryRepository.findById(1001L)
                .orElseThrow();
        ProductEntity productEntity = ProductEntity.builder()
                .externalId("test-product")
                .name("Test product")
                .category(categoryEntity)
                .build();
        productRepository.save(productEntity);

        entityManager.flush();
        Request request = connection.request("""
                select p.EXTERNAL_ID as PROD_EXTID, c.EXTERNAL_ID as CAT_EXTID 
                from PRODUCT p, CATEGORY c
                where p.CATEGORY_ID = c.ID
                    and p.id = ?
                """)
                .parameters(1001L)
                .build();
        assertThat(request)
                .hasNumberOfRows(1)
                .column("PROD_EXTID")
                    .hasValues("product-1.1")
                .column("CAT_EXTID")
                    .hasValues("category-1");
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(
            basePackageClasses = { DbPackage.class }
    )
    static class TestConfig {

    }
}
