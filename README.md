# TooManyOrigins
![https://github.com/MerchantPug/toomanyorigins/issues](https://img.shields.io/github/issues/MerchantPug/toomanyorigins?color=yellow&style=for-the-badge) ![https://github.com/MerchantPug/toomanyorigins/pulls](https://img.shields.io/github/issues-pr/MerchantPug/toomanyorigins?color=lime&style=for-the-badge) ![https://github.com/MerchantPug/toomanyorigins/blob/master/LICENSE](https://img.shields.io/github/license/MerchantPug/toomanyorigins?style=for-the-badge) ![https://apugli.readthedocs.io/en/latest/](https://img.shields.io/readthedocs/apugli?style=for-the-badge)

This is the TooManyOrigins repository where the source code for the TooManyOrigins origins addon is hosted. TooManyOrigins is an addon for Origins that aims to add more origins loosely inspired by the original mod to the mod.
The Origins are also designed to be pretty thematically neutral to ensure that you can put them in most situations.

You can download the mod from the Releases page as a standalone file or through [CurseForge](https://www.curseforge.com/minecraft/mc-mods/toomanyorigins) or [Modrinth](https://modrinth.com/mod/toomanyorigins).

Please don't write the mod's name with spaces.

## Documentation
If you'd like to use TooManyOrigins (Apugli) for Origins datapacking you can read up on the [Documentation](https://apugli.readthedocs.io/en/latest/).
It's recommended to have some knowledge about the task before doing so.

## Implementing the addon into your project

You are able to add TooManyOrigins as a dependency (preferably an optional one) to your workspace through adding these to your project.

Please follow the guide from the [Origins Documentation](https://origins.readthedocs.io/en/latest/guides/addon/workspace_setup/) for general Origins Addon setup, this only covers how to get TMO into your workspace.

**build.gradle**
```groovy
repositories {
    maven {
        name = "Pug's Maven"
        url = 'https://maven.merchantpug.net/releases/'
    }
}

dependencies {
    // For Common sourcesets
    compileOnly(net.merchantpug:TooManyOrigins:${project.tmo_version}-common")
    
    // For Fabric/Quilt Loom
    modImplementation(include("net.merchantpug:TooManyOrigins:${project.tmo_version}-fabric"))
    
    // For ForgeGradle
    implementation(fg.deobf("net.merchantpug:TooManyOrigins:${project.tmo_version}-forge"))
}
```

**gradle.properties**
```properties
tmo_version=[INSERT VERSION HERE]
```
You can find the version number by looking at the releases of the mod and looking at the tag.
