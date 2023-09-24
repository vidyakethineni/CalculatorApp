# Project 2

Project 2 extends on the Project 1 calculator application that mirrors the default iPhone calculator, but with additional functionality for an app icon, changes in device orientation, and logging button clicks using LogCat. 

## Functionality 

In addition to the required functionality from Project 1, the following features and enhancements have been implemented:

* Orientation Persistence: The Project 1 calculator now maintains user inputs even when the orientation changes. This is achieved by overriding certain lifecycle methods to correctly save and restore the state of the calculator.
* Logging: The app includes comprehensive logging using LogCat. Every button click is logged, providing valuable information for debugging and monitoring user interactions.
* App Icon: The app features a calculator icon illustrated by Fikri Azhari and published on Vecteezy, improving its visual appeal and brand identity.
* Landscape Mode Layout: In landscape mode, the calculator layout is optimized with additional buttons, including trignometric and logarithmic functions such as sin, cos, tan, Log 10 and ln.

## Extensions

* Potential extensions include the color-coding of buttons and inclusion of additional buttons to match the landscape layout of the iPhone calculator.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='Project1 Video Walkthrough.gif' title='Project2 Video Walkthrough' width='50%' alt='Project2 Video Walkthrough' />

GIF created with [EzGif](https://ezgif.com/).

## Notes

* Challenges encountered during the coding process included overriding lifecycle methods to restore calculator history on orientation change.

## License

    Copyright [2023] [Vidya Kethineni]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
