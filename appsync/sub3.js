'use strict';
const aws_exports = require('./aws-export').default;
// CONFIG
const AppSync = {
    "graphqlEndpoint": aws_exports.graphqlEndpoint,
    "region": aws_exports.region,
    "authenticationType": aws_exports.authenticationType,
    "apiKey": aws_exports.apiKey,
};
const ApiId = aws_exports.ApiId;

// POLYFILLS
global.WebSocket = require('ws');
global.window = global.window || {
    setTimeout: setTimeout,
    clearTimeout: clearTimeout,
    WebSocket: global.WebSocket,
    ArrayBuffer: global.ArrayBuffer,
    addEventListener: function () { },
    navigator: { onLine: true }
};
global.localStorage = {
    store: {},
    getItem: function (key) {
        return this.store[key]
    },
    setItem: function (key, value) {
        this.store[key] = value
    },
    removeItem: function (key) {
        delete this.store[key]
    }
};
require('es6-promise').polyfill();
require('isomorphic-fetch');

// Require AppSync module
const AUTH_TYPE = require('aws-appsync/lib/link/auth-link').AUTH_TYPE;
const AWSAppSyncClient = require('aws-appsync').default;

// INIT
// Set up AppSync client
const client = new AWSAppSyncClient({
    url: AppSync.graphqlEndpoint,
    region: AppSync.region,
    auth: {
        type: AUTH_TYPE.API_KEY,
        apiKey: AppSync.apiKey
    }
   
});


const gql = require('graphql-tag');

const query = gql(`
query { listPosts {items {
    id
    title
    content
    author
    version
  }}}`);

const Subscription = gql(`
  subscription onCreatePost {
    onCreatePost {
        id
        author
        title
        content
        url
        ups
        downs
        version
    }
  }`);

// APP CODE
client.hydrated().then(function (client) {
    // Now run a query
    client.query({ query: query })
    //client.query({ query: query, fetchPolicy: 'network-only' })   //Uncomment for AWS Lambda
        .then(function logData(data) {
            console.log('results of query: ', JSON.stringify(data));
        })
        .catch(console.error);
    // Now subscribe to results
    const observable = client.subscribe({ query: Subscription});
    const realtimeResults = function realtimeResults(data) {
        console.log('(Realtime Subscription) Subscribing posts -----------> ', data);
    };

    observable.subscribe({
        next: realtimeResults,
        complete: console.log,
        error: console.log,
    });
});