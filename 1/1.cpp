#include <bits/stdc++.h>

using namespace std;

vector<int>* readinput(){
    vector<int> *v = new vector<int>();
    while (cin) {
        int x;
        cin >> x;
        v->push_back(x);
    }
    return v;
}

int part_one(vector<int> &v){
    int result = 0;
    for (int i = 1; i < v.size(); i++) {
        if (v[i] > v[i-1]) result += 1;
    }
    return result;
}

int part_two(vector<int> &v){
    int result = 0;
    for(int i = 4; i < v.size(); i++){
        if(v[i] > v[i-3]) result++;
    }
    return result;
}

int main() {
    vector<int> v = *readinput(); 
    int result = part_one(v);
    int result2 = part_two(v);
    cout << result << endl;
    cout << result2 << endl;
}