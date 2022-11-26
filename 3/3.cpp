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

int oxygen_co2(vector<string> &lines, bool oxygen){
    ll n = lines[0].length();
    set<int> removed = {};
    cout << lines.size() << endl;
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
        char keep = '.';
        if (oxygen){
            keep = count*2 >= (lines.size() - removed.size()) ? '1' : '0';
        } else {
            if (count*2 > (lines.size() - removed.size())){
                keep = '0';
            } else if (count*2 == (lines.size() - removed.size())){
                keep = '0';
            } else {
                keep = '1';
            }
        }
        for(int j = 0; j < lines.size(); j++){
            if(removed.find(j) != removed.end()) continue;
            if(lines[j][i] != keep){
                removed.insert(j);
            }
        }
    }
    for(int i = 0; i < lines.size(); i++){
        if(removed.find(i) == removed.end()){
            return stoi(lines[i], 0, 2);
        }
    }
    return 0;
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
    int oxygen = oxygen_co2(lines, true);
    int co2 = oxygen_co2(lines, false);
    return oxygen * co2;
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