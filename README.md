## 功能：实现视频列表自动静音播放功能
使用ijkplayer，recyclerview实现以下功能

### 需求点：
1. 当列表处于静止状态时，选择列表中第一个满足条件播放的视频进行静音播放。条件是封面图在屏幕中显示超过高度的一半
2. 当视频的封面图在屏幕中显示的高度低于或等于一半时，停止自动静音播放，并选择其他符合条件的视频自动静音播放。
3. 在一屏内有且只有一个视频会自动播放。
4. 手机黑屏或者进入到后台后不再播放，从黑屏状态或者后台回到前台后，恢复静音播放。
5. 如果正在播放的视频播完后，展示封面图。自动选择其他下一个符合条件的直播房间开始静音播放。
6. 其他优化自行发挥


### 视频地址
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10328078/10328078-1586756885/transcode/540p/10328078-1586756885-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10328078/10328078-1586756109272-8008/transcode/540p/10328078-1586756109272-8008-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10000116/10000116-1586748892854-8158/transcode/540p/10000116-1586748892854-8158-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10045957/10045957-1586728344712-9512/transcode/540p/10045957-1586728344712-9512-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10317434/10317434-1586719422/transcode/540p/10317434-1586719422-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10317434/10317434-1586714638/transcode/540p/10317434-1586714638-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10317434/10317434-1586709471/transcode/540p/10317434-1586709471-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10006741/10006741-1586693184479-2688/transcode/540p/10006741-1586693184479-2688-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10317408/10317408-1586680652/transcode/540p/10317408-1586680652-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10058359/10058359-1586678292585-5138/transcode/540p/10058359-1586678292585-5138-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10088339/10088339-1586677400692-9075/transcode/540p/10088339-1586677400692-9075-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10317408/10317408-1586670439438-3011/transcode/540p/10317408-1586670439438-3011-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10243791/10243791-1586656577686-360/transcode/540p/10243791-1586656577686-360-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10328078/10328078-1586646377560-3069/transcode/540p/10328078-1586646377560-3069-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10000116/10000116-1586638171114-6230/transcode/540p/10000116-1586638171114-6230-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10045957/10045957-1586638227507-8568/transcode/540p/10045957-1586638227507-8568-0.m3u8
- https://d3ooprpqd2179o.cloudfront.net/vod/jp/10088339/10088339-1586611112334-51/transcode/540p/10088339-1586611112334-51-0.m3u8
