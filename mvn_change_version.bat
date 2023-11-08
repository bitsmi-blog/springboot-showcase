echo off

rem Usage mvn_change_version <new version>. P.E. mvn_change_version 1.0.0-FINAL

rem %~dp0 -> Script's folder. Ends with "\"
set OLDDIR=%CD%
set SCRIPTDIR=%~dp0

echo Updating project version

rem change framework version
cd %SCRIPTDIR%
call mvn org.codehaus.mojo:versions-maven-plugin:set -DnewVersion=%1

rem restore current directory
chdir /d %OLDDIR%

