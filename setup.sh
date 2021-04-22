#!/bin/bash

sudo groupadd video
sudo usermod -a -G video $USER

# https://elder.dev/posts/open-source-virtual-background/
sudo apt-get install v4l2loopback-dkms
echo options v4l2loopback devices=1 video_nr=20 \
  card_label="fakecam" exclusive_caps=1 | sudo tee \
  /etc/modprobe.d/fakecam.conf
echo v4l2loopback | sudo tee /etc/modules-load.d/fakecam.conf
sudo modprobe -r v4l2loopback
sudo modprobe v4l2loopback

# Install docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh &

# create a network
#docker network create --driver bridge fakecam
