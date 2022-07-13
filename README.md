# Call Monitor

This simple android app keeps a log of your calls and can be accessed via a HTTP server. 

## Root

`curl http://<SERVER_HOST>:<PORT>`

###### Response 

`
{
    "start": "Jul 13, 2022 19:54:29",
    "services": [
        {
            "name": "status",
            "uri": "http://<SERVER_HOST>:<PORT>/services/status"
        },
        {
            "name": "log",
            "uri": "http://<SERVER_HOST>:<PORT>/services/logs"
        }
    ]
}
`

## Status

`curl http://<SERVER_HOST>:<PORT>/services/status`

###### Response 

`{"name": "John Doe", "number": "+12345678902"}`

## Logs

`curl http://<SERVER_HOST>:<PORT>/services/logs`

###### Response 

`
[
    {
        "date": 1657735473853,
        "duration": 3,
        "name": "John Doe",
        "number": "+12345678903",
        "queryCount": 2,
        "uId": "696"
    },
    {
        "date": 1657734888072,
        "duration": 0,
        "name": "Kevin Sneijder",
        "number": "+12345678903",
        "queryCount": 1,
        "uId": "695"
    }
]
`