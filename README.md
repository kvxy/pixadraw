# Pixadraw
#### Pixel Art Sprite Drawer

## Introduction
Pixadraw is an application used for drawing pixel art sprites. 
It lets users create sprites and manipulate each frame utilizing a variety of drawing tools such as the pen tool, erasing tool, etc.
Artists can use this application to create single-frame artworks or even animations as sprites can include multiple frames, and game developers can use it to create assets for their games.
As I enjoy creating pixel art, this project will allow me to delve deeper as to how pixel art software could be structured.

## User Stories
- As a user, I want to be able to create and add multiple *n* by *m* sprites to my collection and give it a name
- As a user, I want to be able to delete sprites in my collection
- As a user, I want to be able to search for sprites by name in my collection
- As a user, I want to be able to edit sprites in my collection
- As a user, I want to be able to add a frame to my sprite
- As a user, I want to be able to delete frames in my sprite
- As a user, I want to be able to go to and edit different frames in my sprite
- As a user, I want to be able to select different tools from the toolbar
- As a user, I want to be able to change the color of my currently selected tool
- As a user, I want to be able to use the pen tool to draw pixels
- As a user, I want to be able to use the erasure tool to erase pixels
- As a user, I want to be able to load and save the state of the application

## Acknowledgements
- Serialization code structure from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.

## Instructions for Grader
- You can generate the first required action related to adding sprites to a collection by clicking the "Create Sprite" button. This brings up a new menu where you can set the sprite's name, width, and height. Once done, press the "Create" button to create and add your sprite.
- You can generate the second required action related to adding sprites to a collection by entering a search query in the search bar in your collection and pressing Enter, which filters the sprites by name.
- You can locate my visual component by either creating a new sprite, or editing an existing sprite by clicking its name in your collection. This will bring up the editor where the canvas is the visual component.
- You can save the state of my application by pressing the "Save File" button in your collection, then, specifying the path to where the application should be saved and pressing the "Save" button.
- You can reload the state of my application by pressing the "Load File" button in your collection, then, specifying the path to where the application should be loaded from and pressing the "Load" button.

## Phase 4: Task 2
Mon Apr 10 22:15:14 PDT 2023
created new sprite named Sprite 1 and added it to collection.

Mon Apr 10 22:15:20 PDT 2023
created new sprite named Sprite 2 and added it to collection.

Mon Apr 10 22:15:23 PDT 2023
removed sprite named Sprite 1 from the collection.

Mon Apr 10 22:15:28 PDT 2023
created new sprite named Sprite 3 and added it to collection.

Mon Apr 10 22:15:31 PDT 2023
removed sprite named Sprite 3 from the collection.

Mon Apr 10 22:15:32 PDT 2023
removed sprite named Sprite 2 from the collection.

Mon Apr 10 22:15:36 PDT 2023
created new sprite named Untitled Sprite and added it to collection.

## Phase 4: Task 3
While program's model package, tools package, and persistence package seems to be somewhat loosely coupled with a decent degree of cohesion, the UI code lacks this and is a mess. The root issue is switching between menus, which forces the menus to need to know about the GraphicalApplication, and since the GraphicalApplication stores menus, this introduces a bidirectional relationship. To alleviate this, I attempted to create a middleman (SwitchMenuListenerBuilder) that handles the interactions between the GraphicalApplication and each menu so "seamlessly" allow for menu switching. However, this solution is imperfect and introduces more issues. 

One way I think this could be improved is using a slightly modified observer pattern. This will turn the GraphicalApplication into the observer and each menu as a subject. When the menu notifies the GraphicalApplication to switch the menu, it will update accordingly. I can also remove some minor unnecessary relationships between classes (e.g. the association from EditorMenu to Sprite) to better improve coupling.