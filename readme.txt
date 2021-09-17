I made a arraylist of all the characters in the input file called   inputFileInAnArrayList variable
I then put that through the c+n algorithm and add all the values to   predictionary variable
there is an arraylist of strings called update which is the output that andrew listed in his example chart
i then populated the dictionary(hashmap) and added the vars to that and i also made an inverted key and value dictionary called invertedictionary
I had another array of integers where i went through the update list and turned the value to their corresponding dictionary key and put it into an array of integers called decodedFileUnicodeValues
i then convert decodedFileUnicodeValues to binary and ouput them to the .bin file


Peter Shen's notes:
There is something wrong with the encoder that I can't quite figure out. Every file, when encoded then decoded, begins with the binary sequence 10101100111011010000000000000101, which returns dictionary values of 345, 436, 0. Shorter files don't return anything at all when decoded, while longer files still retain a lot of their original info, but can't be decoded because there doesn't exist anything at keys 345 and 436 in the dictionary at the very beginning.