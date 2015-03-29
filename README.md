# SchoolProject
Software engineering class project



## Development
### OS X
#### Requirements
- [Maven][maven] - It is recommend to install this with [Homebrew][homebrew] if
  it is not already installed.

```shell
$ brew install maven
```

- [Xcode][xcode] - Install this from the App Store.  This will get you all of
  the basic developer tools needed to install Homebrew and Maven.


--------------------------------------------------------------------------------


## How to start the backend API Server

```shell
$ cd Library
$ mvn clean -P jetty jetty:run
```


## How to stop the backend API Server
Type ctrl-c


## How to view the front end web app
Go to http://localhost:8080/library/public/ui in a web browser.

```shell
$ open http://localhost:8080/library/public/ui
```


--------------------------------------------------------------------------------


## Troubleshooting
### Error about JAVA_HOME
Make sure you have your JAVA_HOME environment variable set.

```shell
$ export JAVA_HOME=/usr
```

If this is not correct for your system, that is ok, it will fall back and use
the default system java home.  For reasons only known to maven, the absence of
JAVA_HOME being set does not work the same way, so just set it, to anything
really, to get past the error message.




[homebrew]: http://brew.sh
[maven]: https://maven.apache.org
[xcode]: https://itunes.apple.com/us/app/xcode/id497799835?mt=12
