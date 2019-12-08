docker build --no-cache --tag timwillnecker/personservice .
docker tag timwillnecker/personservice ec2-3-123-64-174.eu-central-1.compute.amazonaws.com:5000/timwillnecker/personservice
docker push ec2-3-123-64-174.eu-central-1.compute.amazonaws.com:5000/timwillnecker/personservice
ssh -i /usr/local/socoto.pem ec2-user@ec2-3-123-64-174.eu-central-1.compute.amazonaws.com <<EOF
  docker rm -f webservice
  docker rmi ec2-3-123-64-174.eu-central-1.compute.amazonaws.com:5000/timwillnecker/personservice
  docker run --name webservice -p 8080:8080 -d ec2-3-123-64-174.eu-central-1.compute.amazonaws.com:5000/timwillnecker/personservice
EOF

exit
