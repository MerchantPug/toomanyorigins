# TooManyOrigins
![https://jitpack.io/#MerchantPug/toomanyorigins](https://img.shields.io/jitpack/v/github/merchantpug/toomanyorigins?style=for-the-badge) ![https://github.com/MerchantPug/toomanyorigins/issues](https://img.shields.io/github/issues/MerchantPug/toomanyorigins?color=yellow&style=for-the-badge) ![https://github.com/MerchantPug/toomanyorigins/pulls](https://img.shields.io/github/issues-pr/MerchantPug/toomanyorigins?color=lime&style=for-the-badge) ![https://github.com/MerchantPug/toomanyorigins/blob/master/LICENSE](https://img.shields.io/github/license/MerchantPug/toomanyorigins?style=for-the-badge) ![https://apugli.readthedocs.io/en/latest/](https://img.shields.io/readthedocs/apugli?style=for-the-badge)

This is the TooManyOrigins repository where the source code for the TooManyOrigins origins addon is hosted. TooManyOrigins is an addon for Origins that aims to add more origins in the style of the original mod to the mod.

You can download the mod from the Releases page as a standalone file or through [CurseForge](https://www.curseforge.com/minecraft/mc-mods/toomanyorigins) or [Modrinth](https://modrinth.com/mod/toomanyorigins).

Please don't write the mod's name with spaces.

## Documentation
If you'd like to use TooManyOrigins (Apugli) for Origins datapacking you can read up on the [Documentation](https://apugli.readthedocs.io/en/latest/).
It's recommended to have some knowledge about Origins before doing so.

## Implementing the library into your project

You are able to add Apugli as a dependency to your workspace through adding these to your project.

**build.gradle**
```gradle
repositories {
    maven {
		url "https://jitpack.io"
	}
	maven {
		name = "Ladysnake Libs"
		url = 'https://ladysnake.jfrog.io/artifactory/mods'
	}
	maven {
		url = 'https://maven.cafeteria.dev'
		content {
			includeGroup 'net.adriantodt.fabricmc'
		}
	}
	maven {
		url "https://maven.jamieswhiteshirt.com/libs-release"
		content {
			includeGroup "com.jamieswhiteshirt"
		}
	}
	maven {
		url "https://maven.shedaniel.me/"
	}
	maven {
		url "https://maven.terraformersmc.com/"
	}
}

dependencies {
    modImplementation "com.github.MerchantPug:toomanyorigins:${project.tmo_version}"
}
```

**gradle.properties**
```properties
tmo_version=[INSERT VERSION HERE]
```
You can find the version number by looking at the releases of the mod and looking at the tag.
