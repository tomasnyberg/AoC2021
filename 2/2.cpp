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

int main() {
    vector<pair<string, int>> commands; 
    while (cin) {
        string s;
        int n;
        cin >> s;
        cin >> n;
        // add s and n as a pair to commands
        commands.push_back(make_pair(s, n));
    }
    cout << "Part one answer: " << part_one(commands) << endl;
}