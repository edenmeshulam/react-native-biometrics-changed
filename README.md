# react-native-biometrics-changed (ANDROID ONLY)
Check if new fingerprint has enrolled

## Getting started:
npm:
`$ npm install react-native-biometrics-changed`

__Example__:

 Use only after check if there is a finger enrolled!

```js
import RNFingerprintChange from "react-native-biometrics-changed";


  useEffect(() => {
  //check if has enrolled fingerprints and if hardware support
  // and after call this :
    RNFingerprintChange.hasFingerPrintChanged().then((biometricsHasChanged) => {
      if(biometricsHasChanged)
      {
         //do something
      }
    });
  }, []);
 ```

`Pull requests are always welcome :)`


made with ❤ Eden Meshulam

