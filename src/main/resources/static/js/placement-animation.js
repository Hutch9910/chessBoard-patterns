(function () {
    const board = document.querySelector('.placement-board');
        const toggle = document.querySelector('.placement-toggle');

    if (!board) {
        return;
    }

    const cells = Array.from(board.querySelectorAll('.placement-cell'));
    const placementData = window.landingPlacementData;
    const teams = ['team-orange', 'team-green'];
    const placementDelay = 420;
    const settleDelay = 260;
    const fadeDelay = 280;
    const resetDelay = 1500;
        let paused = false;

    function clearCell(cell) {
        cell.className = 'placement-cell';
        cell.replaceChildren();
    }

    function showKnight(cell, team, symbol) {
        const marker = document.createElement('span');
        marker.className = 'placement-marker';
        marker.textContent = symbol || '♞';
        marker.setAttribute('aria-hidden', 'true');
        cell.appendChild(marker);
           cell.classList.add('placement-preview', team);
    }

    function settleCell(cell, team) {
        cell.className = `placement-cell occupied ${team}`;
        cell.replaceChildren();
    }

    async function fadeKnight(cell, team) {
        const marker = cell.querySelector('.placement-marker');
        if (marker) {
            marker.classList.add('fading');
            await delay(fadeDelay);
        }
        settleCell(cell, team);
    }

    function delay(duration) {
        return new Promise(function (resolve) {
                let elapsed = 0;
                let previous = performance.now();

                function tick(current) {
                    if (!paused) {
                        elapsed += current - previous;
                    }
                    previous = current;
                    if (elapsed >= duration) {
                        resolve();
                        return;
                    }
                    window.requestAnimationFrame(tick);
                }

                window.requestAnimationFrame(tick);
        });
    }

        function updateToggle() {
            toggle.textContent = paused ? 'Resume animation' : 'Pause animation';
            toggle.setAttribute('aria-pressed', String(paused));
        }

    function cellForPlacement(piece) {
        return board.querySelector(`[data-row="${piece.row}"][data-column="${piece.column}"]`);
    }

    function showStaticBoard() {
        placementData.pieces.forEach(function (piece, index) {
            const cell = cellForPlacement(piece);
            if (cell) {
                settleCell(cell, teams[index % teams.length]);
            }
        });
    }

    async function animate() {
        while (true) {
            for (let index = 0; index < placementData.pieces.length; index += 1) {
                const piece = placementData.pieces[index];
                const cell = cellForPlacement(piece);
                if (!cell) {
                    continue;
                }
                const team = teams[index % teams.length];
                showKnight(cell, team, '♞');
                await delay(settleDelay);
                await fadeKnight(cell, team);
                await delay(placementDelay);
            }

            await delay(resetDelay);
            cells.forEach(clearCell);
        }
    }

    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
        showStaticBoard();
            toggle.disabled = true;
            toggle.textContent = 'Animation paused';
        return;
    }

        toggle.addEventListener('click', function () {
            paused = !paused;
            updateToggle();
        });

    animate();
}());