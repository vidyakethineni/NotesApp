# Project 7

Project 7 remodels the Project 6 application which allows users to add, edit, and delete notes. The remodel features new screens for user authentication, a modified layout with toolbar compatibility, and data synchronization between devices.

## Functionality 

The following **required** functionality is completed:

  - User accounts are handled through Firebase authentication and are managed      through separate Sign In and Sign Out fragments
  - Notes are displayed in a two-column grid layout, presented within              rounded-corner cards
  - Toolbar features buttons for adding notes, signing out, and deleting notes
  - Notes are saved and synchronized across devices using Firebase Realtime        Database

## Extensions

Potential extensions include:
  - Shows an excerpt of the note description along with the title
  - Implement a search feature that allows users to search for specific            words or phrases within the notes
  - Include folders for grouping notes 
  - password protection for sensitive notes
  - Enable note-sharing with other users for collaboration

  
## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='Project7 Video Walkthrough.gif' title='Project7 Video Walkthrough' width='50%' alt='Project7 Video Walkthrough' />

GIF created with [EzGif](https://ezgif.com/) 

## Notes

Challenges encountered during the coding process included:
  - Implementing addValueEventListener to synchronize notes for same user on       different devices

## License

    Copyright [2023] [Vidya Kethineni]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or          implied.
    See the License for the specific language governing permissions and
    limitations under the License.
