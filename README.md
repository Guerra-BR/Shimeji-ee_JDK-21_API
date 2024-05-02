# Shimeji-ee for Java 21 with REST API: 

Shimeji-ee is a Windows desktop mascot that freely wanders and plays around the screen.  
The mascot is very configurable; its actions are defined through xml and its animations/images can be (painstakingly) customized.  
Shimeji was originally created by [Yuki Yamada of Group Finity](http://www.group-finity.com/Shimeji/).  
This branch of the original Shimeji project not only translates the program/source to English, but adds additional enhancements to Shimeji by Kilkakon and other members of the community.


## Sumary

- Homepage
- Requirements
- How to Start
- Basic Configuration
- Advanced Configuration
- How to Quit
- How to Install and Uninstall
- Source
- Library
- API Docs
- Trouble Shooting

## Homepage

Homepage: http://kilkakon.com/shimeji

## Requeriments

- Java 21
## How to Start

Double Click the Shimeji-ee file (Shimeji-ee.jar).

Right click the tray icon for general options.

Right click a Shimeji for options relating to it.

For a tutorial on how to get Shimeji running, watch this [video](https://www.youtube.com/watch?v=S7fPCGh5xxo) 

You can also watch the [FAQ youtube video](https://www.youtube.com/watch?v=A1y9C1Vbn6Q) if you encounter problems: 

You can also join my Discord group: https://discord.gg/dcJGAn3
## Advanced Configuration
All configuration files are located in the in the conf folders.  In general, none of these should need to be touched.

The logging.properties file defines how logging errors is done.
The actions.xml file specifies the different actions Shimeji can do.  When listing images, only include the file name.  More detail on this file will hopefully be added later.
The behaviors.xml file specifies when Shimeji performs each action.  More detail on this file will /hopefully be added later.
The settings.properties file details which Shimeji are active as well as the windows with which they can interact. These settings can be changed using the program itself.

Each type of Shimeji is configured through:

1. An image set.  This is located in img/[NAME].  The image set must contain all image files specified in the actions file. 
2. An actions file.  Unless img/[NAME]/conf/actions.xml or conf/[NAME]/actions.xml exists, conf/actions.xml will be used.
3. A behaviors file.  Unless img/[NAME]/conf/behaviors.xml or conf/[NAME]/behaviors.xml exists, conf/behaviors.xml will be used.

When Shimeji-ee starts, one Shimeji for every image set in the img folder will be created.  If you have too many image sets, a lot of your computer's memory will be used... so be careful.  Shimeji-ee can eat up to 60% of your system's free memory.  

Shimeji-ee will ignore all the image sets that are in the img/unused folder, so you can hide image sets in there.  There is also a tool, Image Set Chooser, that will let you select image sets at run time.  It remembers previous options via the ActiveShimeji file.  Don't choose too many at once.

The Image Set Chooser looks for the shime1.png image.  If it's not found, no image set preview will be shown.  Even if you're not using an image named shime1.png in your image set, you should include one for the Image Set Chooser's sake.

Editing an existing configuration is fairly straightforward.  But writing a brand new configuration file is very time consuming and requires a lot of trial and error.  Hopefully someone will write a guide for it someday, but until then, you'll have to look at the existing conf files to figure it out.  Basically, for every Behavior, there must be a corresponding action.  Actions and Behaviors can be a sequence of other actions or behaviors.

The following actions must be present for the actions.xml to be valid:

- ChaseMouse
- Fall
- Dragged
- Thrown

The following behaviors must be present for the behaviors.xml to be valid:

- ChaseMouse
- Fall
- Dragged
- Thrown

The icon used for the system tray is img/icon.png
## How to Quit
Right-click the tray icon of Shimeji, Select "Dismiss All"
## How to Install
Download the zipped archieve from the repository

or

Clone this repository by using the command:
```
git clone https://github.com/Guerra-BR/Shimeji-ee_JDK-21_API
```


## How to Uninstall
Delete the folder.

## Library
lib / jna.jar and lib / examples.jar of the JNA library.
JNA follows the LGPL.
lib / AbsoluteLayout.jar from Netbeans.
## API Docs

### POST
#### Sends an action to be executed by the Mascots

```http
  POST /shimeji
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `behaviour` | `string` | **Required**. Name of the action to be performed |
| `mascotId` | `Integer Array` | **Optional**. List of mascot IDs to perform the action |
| `mascotImageSet` | `string` | **Optional**. Type of mascot performing the action |

Performing the request without any of the ID or ImageSet parameters will consequently apply the action to all the Mascots that have the action available


### GET
#### Get all the active Shimejis and Shimeji Types

```http
  GET /shimeji
```
_It does not require a body or params_

#### This will return the following response:
```json
{
    mascotList: string[],
    allowedBehaviours: [
        {
            string: string[]
        },
        ...
    ],
    imageSets: string[],
    shimejiNames: string[],
    ids: int[]
}
```

### Example
#### GET Method
```http
  GET http://localhost:2368/shimeji
```
#### Example 1
```json
{
    shimejiNames: ["Serial Designation V#$#1", "Serial Designation N#$#2", "Cyn#$#3"],
    allowedBehaviours: [
        {
            Serial Designation V: ["ChaseMouse", "CrazyDance", ...],
            Cyn: ["ChaseMouse", "LieDown", ...],
            Serial Designation N: ["ChaseMouse", "SitDown", ...]
        },
        ...
    ],
    imageSets: ["Serial Designation V", "Serial Designation N", "Cyn"],
    mascotList: [
      {
        behaviours: [...],
        id: 2,
        imageSet: "Serial Designation V", 
        name: "Serial Designation V#$#2"
      },
      ...
    ],
    ids: [1, 2, 3]
}
```

_The "mascotList" item is a general compilation of all shimejis active at the time of the request, made to facilitate the construction of applications with UI_


#### POST Method
```http
  POST http://localhost:2368/shimeji
```
#### Example 1
Setting the "Chase Mouse" behaviour to all the shimejis:

```json
body: {
    behaviour: "ChaseMouse"
}
```


#### Example 2
Setting the "CrazyDance" behaviour for all the "Serial Designation V" shimejis:
```json
{
    behaviour: "CrazyDance",
    mascotImageSet: "Serial Designation V"
}
```

#### Example 3
Setting the "LieDown" behaviour for the shimeji N°2, 4 and 7
```json
{
    behaviour: "CrazyDance",
    id: [2, 4, 7]
}
```
## Trouble Shooting
For a tutorial on how to get Shimeji running, watch this [video](https://www.youtube.com/watch?v=S7fPCGh5xxo)

You can also watch the [FAQ Youtube Video](https://www.youtube.com/watch?v=A1y9C1Vbn6Q) if you encounter problems

You can also join the [Discord group](https://discord.gg/dcJGAn3)

Shimeji-ee takes a LOT of time to start if you have a lot of image sets, so give it some time.  Try moving all but one image set from the img folder to the img/unused folder to see if you have a memory problem.  If Shimeji is running out of memory, try editing Shimeji-ee.bat and change "-Xmx1000m" to a higher number.

Sometimes the Shimeji selection does not appear, so I recommend keeping the Shimeji selection when starting the app disabled

If the Shimeji-ee icon appears, but no Shimeji appear:

1. Make sure you have the newest version of Shimeji-ee.
2. Make sure you only have image set folders in your img directory.
3. Make sure you have Java on your system.
4. If you're somewhat computer savvy, you can try running Shimeji-ee from the command line.  Navigate to the Shimeji-ee directory and run this command: "C:\Program Files (x86)\Java\jre6\bin\java" -jar Shimeji-ee.jar
5. Try checking the log (ShimejiLogX.log) for errors.  If you find a bug (which is very likely), post it up on the Shimeji-ee homepage in the issues section.
## Coming Soon
I'm working on more advanced functions like:
#### Targeted movement
- Send shimeji to a user-defined location
- Move/Sit/Interact with user determined window
- Web UI

### In the future
#### Interaction
- Mouse Interaction
- Collisions with each other
## References

 - Original Shimeji artist from Murder Drones: [Polar Summit](https://twitter.com/PolarSummit)
 - Original Shimeji-ee Website: [kilkakon](https://kilkakon.com/shimeji/)
 - Kilkakon [Discord Server](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)

