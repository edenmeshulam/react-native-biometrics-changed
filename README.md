# react-native-biometrics-changed (ANDROID ONLY)
Check if new fingerprint has enrolled

## Getting started:
npm:
`$ npm install react-native-biometrics-changed`

__Example__
  Use only after check if finger has enrolled!
```js
  useEffect(() => {
    RNFingerprintChange.hasFingerPrintChanged().then((biometricsHasChanged) => {
      if(biometricsHasChanged)
      {
         //do something
      }
    });
  }, []);
 ```


