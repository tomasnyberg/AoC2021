#include <bits/stdc++.h>

using namespace std;

int part_one(vector<string> &lines) {
    int counter[lines.size()] = {0};
    for(int i = 0; i < lines.size(); i++){
        for(int j = 0; j < lines[0].length(); j++){
            if(lines[i][j] == '1'){
                counter[j]++;
            }
        }
    }
    string a = "";
    string b = "";
    for(int i = 0; i < lines[0].length(); i++){
        a += counter[i] > lines.size() / 2 ? '1' : '0';
        b += counter[i] <= lines.size() / 2 ? '1' : '0';
    }
    return stoi(a, 0, 2)*stoi(b, 0, 2);
}

int main() {
    string line;
    vector<string> lines = {};
    while(cin){
        getline(cin, line);
        lines.push_back(line);
    }
    cout << "Part one answer: " << part_one(lines) << endl;
}