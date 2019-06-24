# Aeye
Mobile visual testing framework
using Java and appium

# Features
* Take element screenshots
* Compare images and have the diffrences highlighted 

# Usage 
clone and create your iOS or Android tests and use the functions as follows : 

* Screenshot.takeElementScreenshot(AppiumDriver driver, By element, String path)
You just need to pass you driver instance and By object for the element you want to take screenshot. path is for saving the output file.

* Compare.compareImages(String actual, String expected, String result)
the strings are the paths for the actual and expected png files to compare and the output will be saved on result path

The framework is dependent on the image-comparison project by [romankh3](https://github.com/romankh3)
