# define ll long long

#include <bits/stdc++.h>
#include <iostream>
#include <sstream>

inline std::string trim(std::string& str)
{
    str.erase(str.find_last_not_of(' ')+1);         //suffixing spaces
    str.erase(0, str.find_first_not_of(' '));       //prefixing spaces
    return str;
}

using namespace std;

int main(){
    string line;
    vector<int> bingoNums = {};
    string bnums;
    cin >> bnums; 
    for (int i = 0; i <= bnums.length(); i++) {
        if (bnums[i] == ',') {
            bingoNums.push_back(stoi(bnums.substr(0, i)));
            bnums = bnums.substr(i + 1);
            i = 0;
        }
    }
    bingoNums.push_back(stoi(bnums));
    for (int i = 0; i < bingoNums.size(); i++) {
        cout << bingoNums[i] << " ";
    }
    cout << "\n";
}