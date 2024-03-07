#define n 2
char recv = '0'; //переменная для приема и отправки сообщений
char lastRecv = '0'; //дополнительная переменная для хранения предыдущего значения recv
byte buttonPress = HIGH; // переменная для определения нажата кнопка или нет, HIGH – не нажата
byte buttArray[n]  = {1, 2};
String numbersArray[n] = {"1", "2"};
bool butState[n] = {0, 0}; //массив состояний кнопок

long time; // переменная для таймера
void setup() {
  pinMode(13, OUTPUT); //настраиваем пин для встроенного светодиода
  for (byte i = 0; i<n; i++)
    pinMode(buttArray[i], INPUT_PULLUP); //настраиваем пин для кнопки
  digitalWrite(13, LOW); //гасим светодиод на всякий случай
  Serial.begin(9600); //задаем скорость порта
  time = millis(); //стартуем таймер
}
void loop() {
//  if (Serial.available() > 0) { //если есть данные для приема из ком-порта
//    recv = Serial.read(); //считываем 1 символ в переменную recv
//  } //можно использовать Serial.readString() для чтения строк
    for (byte i = 0; i<n; i++)
        buttonsListener(i);

    delay(15); //небольшая задержка для правильной работы всей схемы
}

void buttonsListener(byte buttI){
    bool but = digitalRead(buttArray[buttI]); //считываем состояние кнопки
    if (but == LOW) butState[buttI] = 1; //если нажали, задаем переменной state 1 (true)
    if (but == HIGH && butState[buttI] == 1){ //если кнопку отпустили
        Serial.print(numbersArray[buttI]);
        butState[buttI] = 0; //сбрасываем состояние кнопки
    }
}
