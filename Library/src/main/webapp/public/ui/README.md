# Library - Front end Angular web app


## Development
### OS X
#### Requirements
- [NodeJS][node] 0.12.x - It is recommended to install this with [nvm][nvm] if
  it is not already installed.

```shell
$ nvm install 0.12.1
$ nvm alias default 0.12.1
```

- [npm][npm] 2.7.4 - This most current version of npm does not come with the
  most current version of node.  Easy to install.

```shell
$ npm install --global npm
```

- grunt-cli - This is a npm package.

```shell
$ npm install --global grunt-cli
```


--------------------------------------------------------------------------------


## How to install dependencies
This is only needed if you need to update the bower dependencies (angular),
generate documentation, or run the unit tests locally.

```shell
$ cd Library/src/main/sebapp/public/ui/
$ npm install
```


## How to generate the documentation
Documentation can be auto-generated with [JSDoc][jsdoc].

```shell
$ cd Library/src/main/webapp/public/ui/
$ npm run generate-docs
$ open docs/library/[version]/index.html
```


## How to run the unit tests

```shell
$ cd Library/src/main/webapp/public/ui/
$ npm test
```




[jsdoc]: http://usejsdoc.org
[node]: https://nodejs.org
[npm]: https://npmjs.org
[nvm]: https://github.com/creationix/nvm
