@rem
@rem JitPack Runner Script for Android Library to Publish
@rem
@rem Hekstaroid Android Libraries by StephanusDai Developer
@rem Link : http://sdailover.github.io/
@rem Mail : team (at) sdailover.web.id
@rem Copyright : Copyright (c) 2023 StephanusDai Developer
@rem License : http://sdailover.github.io/license.html
@rem

@if "%DEBUG%" == "" @echo off
echo.
echo StephanusDai Developer Projection
echo.
echo Link http://sdailover.github.io/
echo Copyright (c) 2023 StephanusDai Developer
echo License http://sdailover.github.io/license.html
echo.

setenv SD_SSCMD_CONTINUE ON

@rem ##########################################################################
@rem
@rem  Gradle projects for Maven Publish
@rem
@rem ##########################################################################
@rem Projects using Gradle need to have either
@rem the maven or maven-publish plugin enabled.
@rem
@rem For example, if you add this to your build file:
@rem   apply plugin: 'maven'
@rem   group = 'com.github.YourUsername'
@rem

echo.
echo JitPack will running..
echo.
./gradlew install

echo.
echo to install the jar and pom file in it’s local maven repository.
echo With maven-publish plugin it will run
echo.
./gradlew build publishToMavenLocal



@rem Note that if your project isn’t using a Gradle wrapper JitPack
@rem will build it with a recent version of Gradle.
@rem Therefore it is recommended to use the wrapper.

@rem ##########################################################################
@rem
@rem  Android projects
@rem
@rem ##########################################################################
@rem to build and publish Maven projects. Your Maven group id is harvested
@rem from the top-level pom and then used to locate the installed
@rem artifacts in ~/.m2/repository. Binary jars, source jars and javadoc
@rem can all be picked up from there via the JitPack virtual repository.
@rem

echo.
echo Maven projects for JitPack will running..
echo.
mvn install -DskipTests

@rem ##########################################################################
@rem
@rem  Remove Cache
@rem
@rem ##########################################################################
@rem

echo.
echo Removing cache bulding..
echo.
./gradlew clean