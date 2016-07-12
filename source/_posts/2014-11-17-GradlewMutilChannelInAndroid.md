---
layout: post
title: "[Android]关于Android在Gradlew多渠道打包方式"
tags: [Android,Tools]
categories: [Tools]
---

----
记得以前在Eclipse开发，用的是一个插件，现在好不容易转到IDEA上（下一步就是Android Studio），在文件结构保持和Eclipse一段后，采用了Gradlew进行编译，加上我们公司的香香同学又整理好了Jenkin这个CI自动集成系统，瞬间感觉高大上了。

今天结束了公司的一个版本的开发，回家在整理多渠道包的问题，看了友盟的多渠道方式，有个1.11的在我机器上死活报那个manifest下没有对应渠道的manifest文件，果断采取了copy的方法，也是没办法了。

今天先把代码贴出来，明天在好好整理下，太困了。

```Java
    apply plugin: 'android'
    
    android {
    	compileSdkVersion	19
    	buildToolsVersion	"19.1"
    
    	sourceSets {
    		main {
    			manifest.srcFile 'AndroidManifest.xml'
    			java.srcDirs = ['src']
    			res.srcDirs = ['res']
    			assets.srcDirs = ['assets']
    		}
    
    	}	
    
    	defaultConfig {
    		minSdkVersion	11
    		targetSdkVersion	19
    		versionCode	20141107
    		versionName	"2.2.0"
    	}
    
    	packagingOptions {
            exclude 'META-INF/DEPENDENCIES.txt'
            exclude 'META-INF/LICENSE.txt'
            exclude 'META-INF/NOTICE.txt'
            exclude 'META-INF/NOTICE'
            exclude 'META-INF/LICENSE'
            exclude 'META-INF/DEPENDENCIES'
            exclude 'META-INF/notice.txt'
            exclude 'META-INF/license.txt'
            exclude 'META-INF/dependencies.txt'
            exclude 'META-INF/LGPL2.1'
        }
    
    
    
    //下面一段是将libs/*/*.so文件加入打包
    //如果你的项目是使用Eclipse+ADT建立的，则需要这段代码
        task copyNativeLibs(type: Copy) {
            from(new File('libs')) { include '**/*.so' }
            into new File(buildDir, 'native-libs')
        }
    
        tasks.withType(Compile) { compileTask -> compileTask.dependsOn copyNativeLibs }
    
        clean.dependsOn 'cleanCopyNativeLibs'
    
        tasks.withType(com.android.build.gradle.tasks.PackageApplication) { pkgTask ->
            pkgTask.jniFolders = new HashSet<File>()
            pkgTask.jniFolders.add(new File(buildDir, 'native-libs'))
        }
    	signingConfigs {
    		release {
    			storeFile = file(System.getenv()['APK_SIGNING_KEYSTORE'])	
    			storePassword = System.getenv()['APK_SIGNING_STOREPASS']
    			keyAlias = System.getenv()['APK_SIGNING_KEYALIAS']
    			keyPassword = System.getenv()['APK_SIGNING_KEYPASS']
    		}	
    	}
    
    	buildTypes {
    		release {
    			signingConfig signingConfigs.release
    			runProguard false	
    			proguardFiles	getDefaultProguardFile('proguard-android.txt'), \
    				'proguard-rules.txt'
    
    
    		}
    	}
    
        //渠道
        productFlavors {
            Wandoujia{}
            Qihu360{}
            QQYingyongBao{}
            
        }
    
        //这个是解决lint报错的代码
        lintOptions {
            abortOnError false
        }
    
    
    
    }
    
    
    dependencies {
    	compile fileTree(dir: 'libs', include: ['*.jar'])
    
    	compile project(":libs:libapp")
    	
    	
    }
    
    
    //替换AndroidManifest.xml的UMENG_CHANNEL_VALUE字符串为渠道名称
    android.applicationVariants.all{ variant ->
        variant.processManifest.doLast{
            copy{
                from("${buildDir}/manifests"){
                    include "${variant.dirName}/AndroidManifest.xml"
                }
                into("${buildDir}/manifests/$variant.name")
    
                filter{
                    String line -> line.replaceAll("UMENG_CHANNEL_VALUE", "$variant.name")
                }
    
                variant.processResources.manifestFile = file("${buildDir}/manifests/${variant.name}/${variant.dirName}/AndroidManifest.xml")
    
    
    //            applicationVariants.all { variant ->
                    def file = variant.outputFile
                    def manifestParser = new com.android.builder.core.DefaultManifestParser()
                    def destName = "AppName_"+manifestParser.getVersionName(android.sourceSets.main.manifest.srcFile)+"20141117"+"_"+"$variant.name"+"_release"+".apk"
                    variant.outputFile = new File(file.parent,destName)
    //            }
    
    
    
            }
    
    
        }
    
    
    }
    
    
    ////替换AndroidManifest.xml的UMENG_CHANNEL_VALUE字符串为渠道名称 By Remex Huang
    //android.applicationVariants.all{ variant ->
    //    variant.processManifest.doLast{
    //
    //        //之前这里用的copy{}，我换成了文件操作，这样可以在v1.11版本正常运行，并保持文件夹整洁
    ////${buildDir}是指./build文件夹
    ////${variant.dirName}是flavor/buildtype，例如GooglePlay/release，运行时会自动生成
    ////下面的路径是类似这样：./build/manifests/GooglePlay/release/AndroidManifest.xml
    //def manifestFile = "${buildDir}/manifests/${variant.dirName}/AndroidManifest.xml"
    //
    ////将字符串UMENG_CHANNEL_VALUE替换成flavor的名字
    //def updatedContent = new File(manifestFile).getText('UTF-8').replaceAll("UMENG_CHANNEL_VALUE", "${variant.productFlavors[0].name}")
    //new File(manifestFile).write(updatedContent, 'UTF-8')
    //
    ////将此次flavor的AndroidManifest.xml文件指定为我们修改过的这个文件
    //variant.processResources.manifestFile = file("${buildDir}/manifests/${variant.dirName}/AndroidManifest.xml")
    //}
    //}
```Java