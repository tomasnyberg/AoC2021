#include <bits/stdc++.h>

using namespace std;

int part_one(vector<pair<string, int>> &commands) {
    int x = 0;
    int y = 0;
    for (int i = 0; i < commands.size()-1; i++) {
        if(commands[i].first[0] == 'f'){
            x += commands[i].second;
        } else if (commands[i].first[0] == 'd'){
            y += commands[i].second;
        } else if (commands[i].first[0] == 'u'){
            y -= commands[i].second;
        }
    }
    return x*y;
}

int part_two(vector<pair<string, int>> &commands) {
    int x = 0;
    int y = 0;
    int aim = 0;
    for (int i = 0; i < commands.size()-1; i++) {
        if(commands[i].first[0] == 'f'){
            x += commands[i].second;
            y += aim*commands[i].second;
        } else if (commands[i].first[0] == 'd'){
            aim += commands[i].second;
        } else if (commands[i].first[0] == 'u'){
            aim -= commands[i].second;
        }
    }
    return x*y;
}

int main() {
    vector<pair<string, int>> commands; 
    while (cin) {
        string s;
        int n;
        cin >> s;
        cin >> n;
        commands.push_back(make_pair(s, n));
    }
    cout << "Part one answer: " << part_one(commands) << endl;
    cout << "Part two answer: " << part_two(commands) << endl;
}