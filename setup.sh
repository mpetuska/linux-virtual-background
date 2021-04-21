#!/bin/bash

sudo groupadd video
sudo usermod -a -G video $USER

# https://elder.dev/posts/open-source-virtual-background/
sudo apt install v4l2loopback-dkms v4l2loopback-utils
sudo modprobe -r v4l2loopback
sudo modprobe v4l2loopback devices=1 video_nr=20 card_label="v4l2loopback" exclusive_caps=1

# Install docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh &

# create a network
#docker network create --driver bridge fakecam
