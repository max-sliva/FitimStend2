#define n 3 //кол-во кнопок
char recv = '0'; //переменная для приема и отправки сообщений
char lastRecv = '0'; //дополнительная переменная для хранения предыдущего значения recv
String recvStr = "";
byte buttonPress = HIGH; // переменная для определения нажата кнопка или нет, HIGH – не нажата
byte buttArray[n]  = {9, 10, 11}; //массив с пинами кнопок
byte ledArray[n] = {3, 6, 5};  //массив с пинами светодиодов
String numbersArray[n] = {"9", "10", "11"}; //строки с номерами кнопок (может, не нужны)
bool butState[n] = {0, 0}; //массив состояний кнопок

long time; // переменная для таймера
void setup() {
  pinMode(13, OUTPUT); //настраиваем пин для встроенного светодиода
  for (byte i = 0; i<n; i++){
    pinMode(buttArray[i], INPUT_PULLUP); //настраиваем пин для кнопки
    pinMode(ledArray[i], OUTPUT);
  }
  digitalWrite(13, LOW); //гасим светодиод на всякий случай
  Serial.begin(9600); //задаем скорость порта
  time = millis(); //стартуем таймер
}
void loop() {
  if (Serial.available() > 0) { //если есть данные для приема из ком-порта
    recv = Serial.read(); //считываем 1 символ в переменную recv
    if (recv == '-') {
      send(numbersArray); //todo отправлять кол-во кнопок, а не их порты
    } else if (recv==';'){
      fireLed(recvStr);
      recvStr = "";
    } else recvStr += recv;
  } //можно использовать Serial.readString() для чтения строк
    for (byte i = 0; i<n; i++)
        buttonsListener(i);

    delay(15); //небольшая задержка для правильной работы всей схемы
}

void send(String *numbersArray){
  for (int i = 0; i<n; i++){
    Serial.print(numbersArray[i]);
    if (i!=n-1) Serial.print(',');
  }
  Serial.println(';');
}

void fireLed(String recvStr){
  // Serial.println(recvStr.toInt());
  digitalWrite(recvStr.toInt(), HIGH);
}

void buttonsListener(byte buttI){
    bool but = digitalRead(buttArray[buttI]); //считываем состояние кнопки
    if (but == LOW) butState[buttI] = 1; //если нажали, задаем переменной state 1 (true)
    if (but == HIGH && butState[buttI] == 1){ //если кнопку отпустили
        // Serial.print(numbersArray[buttI]);
        Serial.print(buttArray[buttI]);
        Serial.println(";");
        for (byte i=0; i<n; i++){
          if (i!=buttI) digitalWrite(ledArray[i], LOW);
          else digitalWrite(ledArray[i], HIGH);
        }
        butState[buttI] = 0; //сбрасываем состояние кнопки
    }
}
