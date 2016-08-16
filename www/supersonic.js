SupersonicPlugin = function () {}

SupersonicPlugin.prototype.initialize = function (appKey, userId, success, error) {
	cordova.exec(success, error, 'Supersonic', 'initialize', [appKey, userId]);
};

SupersonicPlugin.prototype.showOfferwall = function (success, error) {
	cordova.exec(success, error, 'Supersonic', 'showOfferwall', []);
};

SupersonicPlugin.prototype.closeOfferwall = function (success, error) {
	cordova.exec(success, error, 'Supersonic', 'closeOfferwall', []);
};

module.exports = new SupersonicPlugin();