import BottomSheet, { BottomSheetModal, BottomSheetView } from '@gorhom/bottom-sheet'
import React, { useCallback, useMemo, useRef, useState } from 'react'
import {
  Button,
  Dimensions,
  GestureResponderEvent,
  Image,
  PanResponder,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native'
import { FlatList, GestureHandlerRootView } from 'react-native-gesture-handler'
import CustomBackdrop from './CustomBackdrop'
import CustomBackground from './CustomBackground'
export default function App() {
  // ref
  const sheetRef = useRef<BottomSheetModal>(null)

  // variables
  const snapPoints = useMemo(() => [84, 169, 339], [])
  //const snapPoints = useMemo(() => ['10%', '50%', '100%'], [])

  // callbacks
  const handleSheetChanges = useCallback((index: number) => {
    console.log('handleSheetChanges', index)
  }, [])

  const [tab, setTab] = useState(0)
  const [page, setPage] = useState(0)

  const data = useMemo(
    () =>
      Array(50)
        .fill(0)
        .map((_, index) => `view ${index + 1}`),
    [],
  )

  const renderItemPage1 = useCallback(
    ({ item }) => (
      <View
        key={item}
        style={{
          height: 85,
          width: Dimensions.get('window').width / 5,
          justifyContent: 'center',
          alignItems: 'center',
        }}>
        <Image
          source={{
            uri: 'https://cdn.shopify.com/s/files/1/1061/1924/products/Flushed_Face_Emoji_large.png?v=1571606037',
            width: 35,
            height: 35,
          }}
        />
        <Text style={{ marginTop: 8, fontSize: 14 }}>{item}</Text>
      </View>
    ),
    [],
  )

  const renderItemPage2 = useCallback(
    ({ item }) => (
      <View
        key={item}
        style={{
          borderColor: 'red',
          borderWidth: 1,
          padding: 8,
          marginVertical: 8,
          justifyContent: 'center',
          alignItems: 'center',
        }}>
        <Text
          style={{
            borderColor: 'red',
            borderWidth: 1,
            marginVertical: 8,
            color: 'white',
          }}>
          {item}
        </Text>
      </View>
    ),
    [],
  )
  const [isHorizontalList, setHorizontalList] = useState(false)
  const [numColumns, setNumColumns] = useState<number | undefined>(undefined)
  const [snapIndex, setSnapIndex] = useState(1)
  return (
    <View
      pointerEvents="box-none"
      style={{
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'transparent',
      }}>
    
        <BottomSheet
          ref={sheetRef}
          index={snapIndex}
          backdropComponent={CustomBackdrop}
          backgroundComponent={CustomBackground}
          snapPoints={snapPoints}
          enableOverDrag={false}
          bottomInset={140}
          detached={true}
          onAnimate={(fromIndex: number, toIndex: number) => {
            console.log(`leon - onAnimate ${fromIndex} - ${toIndex}`)
            setSnapIndex(toIndex)
          }}
          onChange={(index: number) => {
            console.log(`leon: onChange: ${index}`)
            // setSnapIndex(index);
          }}>
          {tab == 0 && (
            <BottomSheetView style={{ flex: 1,  }}>
              {/* tab indicator */}
              <View
                style={{
                  width: '100%',
                  height: 60,
                  flexDirection: 'row',
                  alignItems: 'center',
                  justifyContent: 'center',
                }}>
                <Button title="Amenities" onPress={() => setPage(0)} color={page == 0 ? 'red' : 'black'} />
                <Button title="Plan" color={page == 1 ? 'red' : 'black'} onPress={() => setPage(1)} />
              </View>

              {page == 0 && (
                <View>
                  <FlatList
                    contentContainerStyle={{
                      justifyContent: 'space-evenly',
                      backgroundColor: '#990',
                    }}
                    horizontal={false}
                    data={data}
                    renderItem={renderItemPage1}
                    numColumns={5}
                  />
                  {/* {
                    snapIndex == 1 && 
                    <FlatList
                      style={{ position: 'absolute' }}
                      contentContainerStyle={{
                        justifyContent: 'center',
                        backgroundColor: '#900',
                        opacity: snapIndex == 1 ? 1 : 0,
                      }}
                      horizontal={true}
                      data={data}
                      renderItem={renderItemPage1} />
                  } */}
                </View>
              )}

              {page == 1 && (
                <FlatList
                  keyExtractor={item => item}
                  style={{ flex: 1 }}
                  horizontal={false}
                  contentContainerStyle={[
                    {
                      backgroundColor: '#550',
                      justifyContent: 'center',
                    },
                  ]}
                  data={data}
                  renderItem={renderItemPage2}
                />
              )}
            </BottomSheetView>
          )}

          {tab == 1 && (
            <BottomSheetView
              style={[
                StyleSheet.absoluteFill,
                {
                  justifyContent: 'center',
                  alignItems: 'center',
                  backgroundColor: '#052',
                },
              ]}>
              <Text> Something cool</Text>
            </BottomSheetView>
          )}

          {tab == 2 && (
            <BottomSheetView
              style={[
                StyleSheet.absoluteFill,
                {
                  justifyContent: 'center',
                  alignItems: 'center',
                  backgroundColor: '#069',
                },
              ]}>
              <Text> Something cooler</Text>
            </BottomSheetView>
          )}
        </BottomSheet>

      <View
        style={{
          backgroundColor: '#0cf',
          alignItems: 'center',
          justifyContent: 'space-evenly',
          position:'absolute',
          width:  '100%',
          bottom: 0,
          height: 80,
          flexDirection: 'row',
        }}>
        <Button
          title="Tab 1"
          onPress={() => {
            setTab(0)
          }}
        />
        <Button
          title="Tab 2"
          onPress={() => {
            setTab(1)
          }}
        />
        <Button
          title="Tab1 3"
          onPress={() => {
            setTab(2)
          }}
        />
      </View>
    </View>
  )
}
