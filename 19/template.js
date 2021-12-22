class Signal {
    relatives = [] // Every signal (Beacon?) has a list of relatives, probably the other ones under the same scanner
    constructor(x, y, z, id) {
        this.x = x
        this.y = y
        this.z = z
        this.id = id
    }


    // I've identified every relative position by three values combined into an identifier string
    align(signal) { // Fingerprinting function? 
        const dx = Math.abs(this.x - signal.x)
        const dy = Math.abs(this.y - signal.y)
        const dz = Math.abs(this.z - signal.z)
        this.relatives[signal.id] = signal.relatives[this.id] = [ // Adds this signal to its index in the list of relatives
            Math.hypot(dx, dy, dz).toFixed(5),
            Math.min(dx, dy, dz),
            Math.max(dx, dy, dz)
        ].join(",") 
    }

    compare(signal) { // Finds all the beacons that the other signal also has, and returns these as a list. Can then check if >= 12 
        const result = []
        for (let relative of this.relatives) {
            const index = signal.relatives.indexOf(relative)
            if (index > -1)
                result.push([signal.relatives[index], this.relatives.indexOf(relative), index])
        }
        return result
    }
}

class Scanner {
    signals = []

    addSignal(x, y, z) {
        const newSignal = new Signal(x, y, z, this.signals.length)
        for (let signal of this.signals) {
            signal.align(newSignal)  // everytime we add a signal we update all the relatives of all signals
        }
        this.signals.push(newSignal)
    }

    //Finds how many of this scanners signals have relative signals that are the same as the other scanners

    /*
    By establishing 12 common beacons, you can precisely
    determine where the scanners are relative to each other, 
    allowing you to reconstruct the beacon map one scanner at a time.
    */
    compare(scanner) {
        let max = 0
        for (let there of scanner.signals) {
            for (let here of this.signals) {
                const intersection = there.compare(here)  
                if (intersection.length >= 11) {
                    // There = the other scanners signal
                    // here = This scanners signal
                    // Intersection = how many relatives these two signals have in common ?? 
                    return {there, here, intersection}
                }
            }
        }
    }

    align(scanner, data) {
        console.log(data)
        for (let line of data.intersection) {
            if (line[0].split(",")[1] === "0")
                continue
            const relativeHere = this.signals[line[2]]
            const dx0 = data.here.x - relativeHere.x
            const dy0 = data.here.y - relativeHere.y
            const dz0 = data.here.z - relativeHere.z

            const relativeThere = scanner.signals[line[1]]
            const dx1 = data.there.x - relativeThere.x
            const dy1 = data.there.y - relativeThere.y
            const dz1 = data.there.z - relativeThere.z
            if (Math.abs(dx0) === Math.abs(dy0) || Math.abs(dz0) === Math.abs(dy0) || Math.abs(dx0) === Math.abs(dz0))
                continue
            console.log(dx0, dy0, dz0, dx1, dy1, dz1)

            const map = [0,0,0,0,0,0,0,0,0]

            if (dx0 === dx1)
                map[0] = 1
            if (dx0 === -dx1)
                map[0] = -1
            if (dx0 === dy1)
                map[3] = 1
            if (dx0 === -dy1)
                map[3] = -1
            if (dx0 === dz1)
                map[6] = 1
            if (dx0 === -dz1)
                map[6] = -1
            if (dy0 === dx1)
                map[1] = 1
            if (dy0 === -dx1)
                map[1] = -1
            if (dy0 === dy1)
                map[4] = 1
            if (dy0 === -dy1)
                map[4] = -1
            if (dy0 === dz1)
                map[7] = 1
            if (dy0 === -dz1)
                map[7] = -1
            if (dz0 === dx1)
                map[2] = 1
            if (dz0 === -dx1)
                map[2] = -1
            if (dz0 === dy1)
                map[5] = 1
            if (dz0 === -dy1)
                map[5] = -1
            if (dz0 === dz1)
                map[8] = 1
            if (dz0 === -dz1)
                map[8] = -1

            console.log(map)

            for (let signal of scanner.signals) {
                const old = {
                    x : signal.x,
                    y : signal.y,
                    z : signal.z,
                }
                signal.x = old.x * map[0] + old.y * map[3] + old.z * map[6]
                signal.y = old.x * map[1] + old.y * map[4] + old.z * map[7]
                signal.z = old.x * map[2] + old.y * map[5] + old.z * map[8]
            }
            scanner.position = {
                x : data.here.x - data.there.x,
                y : data.here.y - data.there.y,
                z : data.here.z - data.there.z,
            }
            for (let signal of scanner.signals) {
                signal.x += scanner.position.x
                signal.y += scanner.position.y
                signal.z += scanner.position.z
            }
            console.log(scanner.position)
            break
        }
//        console.log(data.here, relativeHere, data.there, relativeThere)
    }
}

class Solver {
    scanners = []
    // Parse the input
    constructor(data) {
        let scanner
        for (let input of data) {
            if (input.length === 0)
                continue
            if (input[1] === "-") {
                scanner = new Scanner()
                this.scanners.push(scanner)
                continue
            }
            scanner.addSignal(...input.split(",").map(Number))
        }
    }

    align() {
        // Lock beacons in place once we have established their position, beacon at 0,0,0 is locked from the start
        const locked = new Set([0])
        this.scanners[0].position = {x : 0, y : 0, z : 0}
        while (locked.size < this.scanners.length) {
            for (let i = 0; i < this.scanners.length; i++) {
                for (let j = 0; j < this.scanners.length; j++) {
                    if (i === j || !locked.has(i) || locked.has(j))
                        continue
                    let intersection = this.scanners[i].compare(this.scanners[j])
                    // If we can't find 12 or more common beacons
                    if (!intersection)
                        continue
                    console.log(i, j)
                    this.scanners[i].align(this.scanners[j], intersection)
                    locked.add(j)
                }
            }
        }
    }
    get result() {
        this.align()
        const beacons = new Set()
        for (let scanner of this.scanners)
            for (let signal of scanner.signals)
                beacons.add([signal.x,signal.y,signal.z].join(","))

        return beacons.size
    }
}

class Solver2 extends Solver {
    get result() {
        this.align()
        let max = 0
        for (let here of this.scanners)
            for (let there of this.scanners)
                max = Math.max(max, Math.abs(there.position.x - here.position.x) + Math.abs(there.position.y - here.position.y) + Math.abs(there.position.z - here.position.z))
        return max
    }
}

export function part1 (data, raw) {
//    let solver = new Solver(raw.split(",").map(Number))
    let solver = new Solver(data)

    return solver.result
}

export function part2 (data, raw) {
//    let solver = new Solver2(raw.split(",").map(Number))
    let solver = new Solver2(data)

    return solver.result
}
