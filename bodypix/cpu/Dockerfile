FROM ubuntu
# Install node
RUN apt update && apt install -y curl make build-essential \
    && curl -sL https://deb.nodesource.com/setup_12.x | bash - \
    && apt-get -y install nodejs \
    && mkdir /.npm \
    && chmod 777 /.npm
# Ensure we can get enough GPU memory
# Unfortunately tfjs-node-gpu exposes no gpu configuration :(
ENV TF_FORCE_GPU_ALLOW_GROWTH=true
# Install node package dependencies
WORKDIR /src
COPY package.json /src/
RUN npm install
# Setup our app as the entrypoint
COPY app.js /src/
ENTRYPOINT node /src/app.js
