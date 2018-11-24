storage of gcloud console has 5 GIG and is discarded after 20 mins from the end of the session

######## instructions ########
0.
cd ~
git clone https://github.com/slzdevsnp/proj 

1
cd ~/proj/util
mv ./gitconfig ~/.gitconfig

2.
##git store credentials
##store git credentials per project
cd ~/proj
git config credential.helper store
git push https://github.com/slzdevsnp/proj.git

Username: slzdevsnp
Password: <type your password>

####### instructions end ######


open a gcp console. 
which is a linux docker image  with cloud preinstalled 
the files stored there have a persistent storage 


>gcloud auth login (to add gmail login to a list of access )

>cloud config set account zslava@gmail.com  # set an active account

>gcloud auth list   #to see your gcp logins  * means active login

>gcloud config list project #to see your current project 

>gcloud config list project  #to set a new project

>gcloud config set project valak                                     #to change a current project
>gcloud config set project mf-dev-slava-training
 
>gcloud alpha cloud-shell ssh  #connect by ssh to your gcp console

#copy files by scp 
> gcloud alpha  cloud-shell scp --help
> gcloud alpha  cloud-shell scp localhost:/Users/zimine/t.sql   localhost:/Users/zimine/t.Rmd  cloudshell:~


#copy local files to a bucket 
>gsutil  cp ./cloudsql/* gs://qwiklabs-gcp-29bd7ee5dc1fbbb6/sql/

#
