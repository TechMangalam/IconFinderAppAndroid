# IconFinderAppAndroid
App to download icons from iconfinder API


## Setup
- Get an api key from IconFinder
- Create a `secrets.properties` file in the root of the project
- Paste the API key as `API_KEY=<api_key>`
- Add the below function in `app/build.gradle` file
```groovy
static def getApiKey(){
    def props = new Properties()
    try {
        props.load(new FileInputStream(new File('secrets.properties')))
        return props['API_KEY']
    } catch(ignored) {
        return ""
    }
}
```
- Add the API Key in build configuration
```groovy
android {
    //...
    buildTypes {
        debug {
            // for debug
            buildConfigField "String", "API_KEY", "\"${getApiKey()}\""
        }
        release {
            // for release
            buildConfigField "String", "API_KEY", "\"${getApiKey()}\""
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```
- Sync, Build & run!!! :)


[IconFinder](https://www.iconfinder.com/)

[IconFinder docs](https://developer.iconfinder.com/reference/overview-1)

## Tech
- Jetpack Compose 
- Hilt
- Retrofit
