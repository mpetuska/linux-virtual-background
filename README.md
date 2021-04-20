# Linux Virtual Background
Convenient virtual background provider for your video calls. Works as a virtual camera
on linux systems and as such can be used with most (if not all) video call apps.

## Setup
All required setup for Debian-based distros are done 
by the utility script (update as required).
```bash
./setup.sh
```

Aditionally, if you wish to change the background to your own, 
drop your custom backgrounds into `./fakecam/backgrounds/` folder 
and set the new file name at `./fakecam/fake.py#L8`.
Alternatively, you can just override `./fakecam/backgrounds/background.jpg`(default) file.

To turn the hologram effect off, change `./fakecam/fake.py#L9` to `False`

## Start
Note that this will not work on chromium-based browsers. Works fine on firefox & messaging apps like Teams or Slack
```bash
docker-compose up -d --build
```

## Debug
```bash
sudo apt install ffmpeg
ffplay /dev/video20
```

## Stop
```bash
docker-compose down
```

## Performance
Default setup works in CPU mode which is a lot less performant (expect camera lags).
If you have NVIDIA GPU available on your machine, 
you can swith to GPU mode, which performs significantly better:
1. Uncomment `./docker-compose.yml#L8`
2. Comment-out `./docker-compose.yml#L9`

## Credits
Most of it is based on the wonderful [article](https://elder.dev/posts/open-source-virtual-background/)
by **Benjamin Elder**. I've just fixed some minor issues and wrapped it into further
abstractions for convenience.
