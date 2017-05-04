# http4s gzip test

This is a test of http4s' gzip functionality. See issues below with not being able to decompress the gzipped responses.

## Testing clients

### curl and gunzip

```bash
$ sbt run
# In a new terminal tab
$ curl -s -H 'Accept-Encoding: gzip' http://localhost:8080/hello/world
# => �V�M-.NLOU�R�H����Q(�/�IQ�

$ curl -s -H 'Accept-Encoding: gzip' http://localhost:8080/hello/world | gunzip -c
# => {"message":"Hello, world"}gunzip: (stdin): unexpected end of file

$ curl -s -H 'Accept-Encoding: gzip' http://localhost:8080/hello/world > /tmp/hello-world.gz
$ gunzip -c /tmp/hello-world.gz
# => {"message":"Hello, world"}gunzip: /tmp/hello-world.gz: unexpected end of file
#    gunzip: /tmp/hello-world.gz: uncompress failed

# This works as intended
$ curl -s -H 'Accept-Encoding: gzip' --compressed http://localhost:8080/hello/world
# => {"message":"Hello, world"}
```

### [scalaj-http](https://github.com/scalaj/scalaj-http)

The scalaj-http client provides automatic gzip and deflate support, but it fails to decompress the gzipped http4s
response.

```scala
import scalaj.http.Http

Http("http://localhost:8080/hello/world").asString.body
// => ""

// Tell the client to not accept compressed responses
Http("http://localhost:8080/hello/world").compress(false).asString.body
// => {"message":"Hello, world"}
```

## gzip file type

The file type as reported by `file` differs between http4s and other gzipped responses. Not sure if this has
anything to do with the problems above.

```bash
$ curl -s -H 'Accept-Encoding: gzip' http://localhost:8080/hello/world > /tmp/hello-world.gz
$ file /tmp/hello-world.gz
# => /tmp/hello-world.gz: gzip compressed data, from FAT filesystem (MS-DOS, OS/2, NT)

$ curl -s -H 'Accept-Encoding: gzip' https://www.google.com > /tmp/google.gz
$ file /tmp/google.gz
# => /tmp/google.gz: gzip compressed data, max compression

$ curl -s -H 'Accept-Encoding: gzip' http://www.cnn.com > /tmp/cnn.gz
$ file /tmp/cnn.gz
# => /tmp/cnn.gz: gzip compressed data, from Unix
```
