supersonic-phonegap
===================

> Supersonic plugin for Android. This allows you to show the Supersonic Offerwall on you app.

## Preparation:
Before you can begin using this plugin, you need to set up a Supersonic account. When you do so, you will obtain an appKey. Start by going to the [Supersonic](http://www.supersonicads.com/) site and click on the **Sign Up** button.

## Installation:

### Using Supersonic without PhoneGap Build

From your main project directory, run:

```bash
phonegap local plugin add https://github.com/SeVeNDuS/supersonic-phonegap
```

This will download the Supersonic plugin and add it to your project. (Note that only Android PhoneGap projects are supported.)

### Using Supersonic with PhoneGap Build

TODO

## Initialize the Supersonic SDK

Initialize Supersonic from your `onDeviceReady:` function like this:

```javascript
window.plugins.supersonic.initialize({ appKey: 'SUPERSONIC_APP_KEY', userId: 'APP_USERID' });
```

** For Android projects, it is mandatory that you include the Google Play Services SDK.**

### Offerwall

To show Supersonic Offerwall you must execute the following code:

```javascript
window.plugins.supersonic.showOfferwall();
```
