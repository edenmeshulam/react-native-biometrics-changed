# react-native-biometrics-changed (ANDROID ONLY)
Check if new fingerprint has enrolled

Installtion:
npm install react-native-biometrics-changed

Exampel:
 # Use only after check if finger has enrolled!
 
  useEffect(() => {
    RNFingerprintChange.hasFingerPrintChanged().then((biometricsHasChanged) => {
      if(biometricsHasChanged)
      {
        alert("New finger has enrolled")
      }
    });
  }, []);


