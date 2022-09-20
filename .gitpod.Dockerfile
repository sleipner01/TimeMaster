FROM gitpod/workspace-full-vnc

USER root

RUN sudo apt-get update && sudo apt-get install -y matchbox && sudo apt-get clean && sudo rm -rf /var/cache/apt/* && sudo rm -rf /var/lib/apt/lists/* && sudo rm -rf /tmp/*

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
             && sdk install java 16.0.1.hs-adpt \
             && sdk default java 16.0.1.hs-adpt"


# FROM gitpod/workspace-full-vnc

# USER root

# RUN sudo apt-get update && sudo apt-get install -y matchbox && sudo apt-get clean && sudo rm -rf /var/cache/apt/* && sudo rm -rf /var/lib/apt/lists/* && sudo rm -rf /tmp/*

# USER gitpod

# RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
#              && sdk install java 17.0.1.hs-adpt \
#              && sdk default java 17.0.1.hs-adpt"

# RUN apt-get update \
#     && apt-get install -y openjfx libopenjfx-java matchbox \
#     && apt-get clean && rm -rf /var/cache/apt/* && rm -rf /var/lib/apt/lists/* && rm -rf /tmp/*
