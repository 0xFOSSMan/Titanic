# Titanic
A QOL client for Legacy Minecraft Versions.

![image](img/mods.png)

## Which versions will this client support?
The client will support Beta 1.1_02 and Beta 1.7.3.

## Is this client bannable on servers?
No, we are in contact with server owners and plan to satisfy all rules.

Titanic will come with a Bukkit API that will allow servers to stop the use of certain features on the server.

## Development Roadmap
- [x] General designing process
- [x] Create a base for simple features, will prob use a modified version of [Settings](https://github.com/Noxiuam/Settings)
- [ ] Create a way to detect JBanned users, notify them if they are banned
- [x] Create the UI framework with different paths for each menu (it will work similarly to how endpoints work in express servers)
- [x] Implement a basic cosmetic system for capes only, wings and emotes will come at a later time
- [ ] Bukkit API to allow servers to disable certain features on the server
- [x] A menu to modify settings
- [x] Make settings and mod states save via a mod config

## Bug Fixes
- [x] Fix chunk crash when loading them too fast
- [ ] Fix chest inventory "chest" text not being capitalized properly
- [ ] Fix armor models not moving with player swinging animation
- [x] Fix chat crash when someone sends a bunch of characters
- [x] Fix slow chunk rendering
- [x] Fix capes models when sneaking and other stuff
- [x] Fix player head model when sneaking and unsneaking
- [x] Remove "Saving level..." when on a server, it doesn't save anything

## QOL Suggestions Roadmap
- [x] ~~Convenient~~ Modern Inventory
- [ ] Local player tab list mod (players you have loaded since beta has no way of getting new players)
- [x] Implement screenshot taking with just F2 instead of F1 + F2

### Performance
- [ ] Toggle crop rendering
- [ ] Toggle block particle rendering
- [ ] Entity culling
- [ ] Block culling

### Perspective
- [x] 3rd Person View Bobbing
- [x] Modern Perspective Switching
- [ ] Camera FOV
- [ ] Hand FOV
- 
### Game Overlay
- [x] Toggle F3 instead of holding
- [ ] Nether Portal overlay/sounds
- [ ] A way to toggle fire in first person or lower its position

### Chat
- [ ] Scrolling
- [ ] Copy & Pasting
- [ ] Strip chat colors entirely
- [ ] Make the chat a draggable HUD mod
- [x] Background Toggling
- [x] Message History
- 
### World Editor
- [ ] Static time options: Morning, Noon, Evening, Midnight
- [ ] Brightness slider
- [ ] Render Distance slider

### Animations
- [ ] Sword block hitting (visual only)
- [ ] Smooth sneaking animation
