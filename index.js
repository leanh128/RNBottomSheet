/**
 * @format
 */

import { AppRegistry } from 'react-native';
import 'react-native-gesture-handler';
import App from './App';
import { name as appName } from './app.json';
import {
    TouchableOpacity,
    TouchableHighlight,
    TouchableWithoutFeedback,
  } from '@gorhom/bottom-sheet';
AppRegistry.registerComponent(appName, () => App);
