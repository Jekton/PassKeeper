
PasswordsKeeper
===============
PasswordsKeeper is a simple app used to keep all your passwords
at one place with a single password.


Feature
-------
1. Add/delete/update password item
2. Offline backup

Note: password data is located in `/sdcard/password-keeper.dat`


Download
--------
Prebuilt apks can be found in the [release][1] page.


Build
-----
Use [KeyGen][2] to generate your own IV and salt and replace
the corresponding values in [cipher_params_keeper.cpp][3]

> Usage: `java KeyGen [seek] [#bytes]`

You can use a single seek to generate 32 bytes data or use two
seeks to generate IV and salt separately.

Note: While generating your own IV and salt, make sure that you
use the same params persistently.


Port to other platforms
-----------------------
Password data format can be found in [here][4]



License
-------
    Copyright (c) 2018 Jekton

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.




[1]: https://github.com/Jekton/PasswordsKeeper/releases
[2]: https://github.com/Jekton/PasswordsKeeper/blob/master/app/KeyGen.java
[3]: https://github.com/Jekton/PasswordsKeeper/blob/master/app/src/main/cpp/cipher_params_keeper.cpp
[4]: https://github.com/Jekton/PasswordsKeeper/blob/master/doc/data-format.md