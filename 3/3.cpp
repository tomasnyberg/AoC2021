#include <bits/stdc++.h>

#define ll long long

using namespace std;

void fill_counter(int counter[], vector<string> &lines){
    for(int i = 0; i < lines.size(); i++){
        for(int j = 0; j < lines[0].length(); j++){
            if(lines[i][j] == '1'){
                counter[j]++;
            }
        }
    }
}
int part_one(vector<string> &lines) {
    int counter[lines.size()] = {0};
    fill_counter(counter, lines);
    string a = "";
    string b = "";
    for(int i = 0; i < lines[0].length(); i++){
        a += counter[i] > lines.size() / 2 ? '1' : '0';
        b += counter[i] <= lines.size() / 2 ? '1' : '0';
    }
    return stoi(a, 0, 2)*stoi(b, 0, 2);
}

int part_two(vector<string> &lines){
    ll n = lines[0].length();
    set<int> removed = {};
    for(int i = 0; i < n; i++){
        if(removed.size() == lines.size() - 1){
            break;
        }
        ll count = 0;
        for(int j = 0; j < lines.size(); j++){
            // If j is in removed, continue
            if(removed.find(j) != removed.end()) continue;
            if(lines[j][i] == '1'){
                count++;
            }
        }
        char keep = count*2 >= (lines.size() - removed.size()) ? '1' : '0';
        for(int j = 0; j < lines.size(); j++){
            if(removed.find(j) != removed.end()) continue;
            if(lines[j][i] != keep){
                removed.insert(j);
            }
        }
        cout << "current state:" << endl;
        for(int j = 0; j < lines.size(); j++){
            if(removed.find(j) != removed.end()) continue;
            cout << lines[j] << endl;
        }
    }
    for(int i = 0; i < lines.size(); i++){
        if(removed.find(i) == removed.end()){
            return stoi(lines[i], 0, 2);
        }
    }
    return 0;
}

int main() {
    string line;
    vector<string> lines = {};
    while(cin){
        getline(cin, line);
        lines.push_back(line);
    }
    int ans_two = part_two(lines);
    cout << "Part one answer: " << part_one(lines) << endl;
    cout << "Part two answer: " << ans_two << endl;
}