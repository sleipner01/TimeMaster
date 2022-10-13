# JSON file storage

## File format

The program uses Jackson to parse an `ArrayList` of `Employees` to a `JSON` document and saves it to a local folder.

```JSON
[
    {
        "id": "24b9f687-0de5-496f-88e2-b17938e2284e",
        "name": "Amalie",
        "workdays": [
            {
                "date": [2022, 10, 13],
                "timeIn": [13, 16, 17, 775123000],
                "timeOut": [13, 16, 22, 277309000]
            }
        ],
        "atWork": false
    },
    {
        "id": "e5698699-f449-4548-b085-b5b92f22e659",
        "name": "Håvard",
        "workdays": [
            {
                "date": [2022, 10, 13],
                "timeIn": [13, 16, 24, 123578000],
                "timeOut": [13, 16, 26, 23432000]
            }
        ],
        "atWork": false
    },
    {
        "id": "8f7105b4-debf-4f60-89a2-4b29d2753935",
        "name": "Karen",
        "workdays": [
            {
                "date": [2022, 10, 13],
                "timeIn": [13, 16, 28, 173469000],
                "timeOut": null
            }
        ],
        "atWork": true
    },
    {
        "id": "e5e79a21-b18b-4040-9898-d6ab77340a5e",
        "name": "Magnus",
        "workdays": [
            {
                "date": [2022, 10, 13],
                "timeIn": [13, 16, 31, 223879000],
                "timeOut": null
            }
        ],
        "atWork": true
    }
]
```

## Implicit storage Vs. Document metaphor

We have chosen implicit storage to make the user experience better.
When ever a change occurs within the program, the changes are saved to the JSON file.

Storing using the document metaphor does not really make sense for this type of program, since it is constantly changing.
Forcing the user to save manually would make the user experience worse, and the program a hassle to use.
