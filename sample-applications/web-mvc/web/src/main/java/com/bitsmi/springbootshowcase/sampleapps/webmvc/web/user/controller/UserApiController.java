package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRegistryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRetrievalApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.WebConstants;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.request.PaginatedRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.mapper.PaginationMapper;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.ChangeUserPasswordRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.CreateUserRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.UpdateUserRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.User;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.mapper.UserApiMapper;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseStatus(HttpStatus.OK)
@Observed(name = "api.user")
public class UserApiController
{
    private final UserRetrievalApplicationService userRetrievalApplicationService;
    private final UserRegistryApplicationService userRegistryApplicationService;
    private final UserApiMapper userApiMapper;
    private final PaginationMapper paginationMapper;

    public UserApiController(
            UserRetrievalApplicationService userRetrievalApplicationService,
            UserRegistryApplicationService userRegistryApplicationService,
            UserApiMapper userApiMapper,
            PaginationMapper paginationMapper
    ) {
        this.userRetrievalApplicationService = userRetrievalApplicationService;
        this.userRegistryApplicationService = userRegistryApplicationService;
        this.userApiMapper = userApiMapper;
        this.paginationMapper = paginationMapper;
    }

    @GetMapping
    public PaginatedResponse<User> getUsers(
            @Valid PaginatedRequest paginatedRequest
    ) {
        final Pagination pagination = paginationMapper.fromRequest(
                paginatedRequest.withDefaults(WebConstants.PAGINATION_DEFAULT_PAGE, WebConstants.PAGINATION_DEFAULT_PAGE_SIZE)
        );
        final PaginatedData<com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User> results = userRetrievalApplicationService.findAllUsers(pagination);
        return PaginatedResponse.<User>builder()
                .data(results.data()
                        .stream()
                        .map(userApiMapper::mapResponseFromModel)
                        .toList()
                )
                .pagination(paginationMapper.fromDomain(results.pagination()))
                .pageCount(results.pageCount())
                .totalCount(results.totalCount())
                .totalPages(results.totalPages())
                .build();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id)
    {
        return userRetrievalApplicationService.findUserById(id)
                .map(userApiMapper::mapResponseFromModel)
                .orElseThrow(() -> HttpClientErrorException
                        .create(HttpStatus.NOT_FOUND,
                                "Element (%s) of type (%s) not found".formatted(
                                        id,
                                        User.class
                                ),
                                null,
                                null,
                                StandardCharsets.UTF_8
                        )
                );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid CreateUserRequest request)
    {
        try {
            com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User createdUser = userRegistryApplicationService.createUser(
                    request.username(),
                    request.password(),
                    request.completeName(),
                    request.groups()
            );

            return userApiMapper.mapResponseFromModel(createdUser);
        }
        catch(ElementNotFoundException e) {
            throw HttpClientErrorException.create(HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    null,
                    null,
                    StandardCharsets.UTF_8);
        }
        catch(ElementAlreadyExistsException e) {
            throw HttpClientErrorException.create(HttpStatus.CONFLICT,
                    e.getMessage(),
                    null,
                    null,
                    StandardCharsets.UTF_8);
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request)
    {
        try {
            com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User updatedUser = userRegistryApplicationService.updateUser(
                    id,
                    request.username(),
                    request.completeName(),
                    request.groups()
            );

            return userApiMapper.mapResponseFromModel(updatedUser);
        }
        catch(ElementNotFoundException e) {
            throw HttpClientErrorException.create(HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null,
                    null,
                    StandardCharsets.UTF_8);
        }
    }

    @PostMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserPassword(@PathVariable Long id, ChangeUserPasswordRequest request)
    {
        try {
            userRegistryApplicationService.changeUserPassword(id, request.password());
        }
        catch(ElementNotFoundException e) {
            throw HttpClientErrorException.create(HttpStatus.NOT_FOUND,
                    e.getMessage(),
                    null,
                    null,
                    StandardCharsets.UTF_8);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id)
    {
        userRegistryApplicationService.deleteUser(id);
    }
}
