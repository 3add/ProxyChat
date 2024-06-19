# ProxyChat 
ProxyChat is a velocity plugin made by 3add.

## The latest version currently has 2 features:
- **/staffchat <message>** -> Send a staff message to all proxied players with permission "**staffchat.network**". 
![image](https://github.com/3add/ProxyChat/assets/141489004/5e585414-0b18-48d0-ab25-7ee3db1da1a2)

*The permission to use "**/staffchat <message>**" is "**staffchat.network**" it also has "**/sc <message>**" as an alias.*

- **/networkbroadcast <message>** -> Send a staff message to all proxied players. 

![image](https://github.com/3add/ProxyChat/assets/141489004/a14e1467-c862-43b9-892b-eefafa7ef184)

*The permission to use "**/networkbroadcast <message>**" is "**broadcast.network**" it also has "**/netbc <message>**" as an alias.*

## PlaceHolders in Config.yml
### Network Broadcast
- broadcastmessage -> The message you wish to broadcast
- broadcastserver - > The server of the player who is network broadcasting.
### StaffChat
- playerserver -> The server of the player that is staff chatting.
- playername -> The name of the player that is staff chatting.
- staffmessage -> The message which the player that is staff chatting wishes to send

*If you wish to use a placeholder in your format you must declare it like "**%placeholder%**", the plugin will parse it.*

