pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // Configure the Maven repository address for the HMS Core SDK.
        maven { url=uri("https://developer.huawei.com/repo/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Configure the Maven repository address for the HMS Core SDK.
        maven { url=uri("https://developer.huawei.com/repo/") }
    }
}

rootProject.name = "JetpackDex"
include(":app")
