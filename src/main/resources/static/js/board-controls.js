(function () {
    const viewport = document.getElementById('board-viewport');
    const stage = document.getElementById('board-stage');
    const resetButton = document.getElementById('reset-view');
    const canvas = document.getElementById('board-canvas');
    const visualisationForm = document.getElementById('visualisation-form');
    const largeBoardWarning = document.getElementById('large-board-warning');

    if (!viewport || !stage || !canvas) {
        return;
    }

    const board = window.boardData || { side: 1, pieces: [] };
    const context = canvas.getContext('2d');
    const piecesByCoordinate = new Map(board.pieces.map(piece => [`${piece.column},${piece.row}`, piece]));
    let scale = 1;
    let offsetX = 0;
    let offsetY = 0;
    let dragging = false;
    let pointerX = 0;
    let pointerY = 0;

    function getInitialView(width, height) {
        if (board.pieces.length === 0) {
            return { scale: 1, offsetX: 0, offsetY: 0 };
        }

        const columns = board.pieces.map(piece => piece.column);
        const rows = board.pieces.map(piece => piece.row);
        const minColumn = Math.min(...columns);
        const maxColumn = Math.max(...columns);
        const minRow = Math.min(...rows);
        const maxRow = Math.max(...rows);
        const boardSize = Math.min(width, height) * 0.78;
        const cellSize = boardSize / board.side;
        const occupiedWidth = Math.max(1, maxColumn - minColumn + 1) * cellSize;
        const occupiedHeight = Math.max(1, maxRow - minRow + 1) * cellSize;
        const targetSize = Math.min(width, height) * 0.78;
        const initialScale = Math.min(targetSize / occupiedWidth, targetSize / occupiedHeight);
        const boardLeft = (width - boardSize) / 2;
        const boardTop = (height - boardSize) / 2;
        const centerX = width / 2;
        const centerY = height / 2;
        const occupiedCenterX = boardLeft + ((minColumn + maxColumn + 1) / 2) * cellSize;
        const occupiedCenterY = boardTop + ((minRow + maxRow + 1) / 2) * cellSize;

        return {
            scale: Math.max(1, Math.min(100, initialScale)),
            offsetX: (centerX - occupiedCenterX) * initialScale,
            offsetY: (centerY - occupiedCenterY) * initialScale
        };
    }

    function drawBoard() {
        const bounds = viewport.getBoundingClientRect();
        const ratio = window.devicePixelRatio || 1;
        const width = bounds.width;
        const height = bounds.height;
        const boardSize = Math.min(width, height) * 0.78;
        const cellSize = boardSize / board.side;
        const boardLeft = (width - boardSize) / 2;
        const boardTop = (height - boardSize) / 2;
        const centerX = width / 2;
        const centerY = height / 2;
        const scaledCellSize = cellSize * scale;
        const scaledLeft = centerX + (boardLeft - centerX) * scale + offsetX;
        const scaledTop = centerY + (boardTop - centerY) * scale + offsetY;

        canvas.width = Math.max(1, Math.floor(width * ratio));
        canvas.height = Math.max(1, Math.floor(height * ratio));
        canvas.style.width = `${width}px`;
        canvas.style.height = `${height}px`;
        context.setTransform(ratio, 0, 0, ratio, 0, 0);
        context.clearRect(0, 0, width, height);
        context.fillStyle = '#e7e3db';
        context.fillRect(0, 0, width, height);

        const firstColumn = Math.max(0, Math.floor(-scaledLeft / scaledCellSize));
        const lastColumn = Math.min(board.side - 1, Math.ceil((width - scaledLeft) / scaledCellSize));
        const firstRow = Math.max(0, Math.floor(-scaledTop / scaledCellSize));
        const lastRow = Math.min(board.side - 1, Math.ceil((height - scaledTop) / scaledCellSize));

        context.save();
        context.beginPath();
        context.rect(0, 0, width, height);
        context.clip();

        for (let row = firstRow; row <= lastRow; row++) {
            for (let column = firstColumn; column <= lastColumn; column++) {
                const x = scaledLeft + column * scaledCellSize;
                const y = scaledTop + row * scaledCellSize;
                const nextX = x + scaledCellSize;
                const nextY = y + scaledCellSize;
                context.fillStyle = '#e7e3db';
                context.fillRect(x, y, Math.max(1 / ratio, nextX - x), Math.max(1 / ratio, nextY - y));
            }
        }

        for (let row = firstRow; row <= lastRow; row++) {
            for (let column = firstColumn; column <= lastColumn; column++) {
                const piece = piecesByCoordinate.get(`${column},${row}`);
                if (!piece) {
                    continue;
                }
                const x = scaledLeft + column * scaledCellSize;
                const y = scaledTop + row * scaledCellSize;
                context.fillStyle = piece.color || '#172026';
                context.fillRect(x, y, Math.max(1 / ratio, scaledCellSize), Math.max(1 / ratio, scaledCellSize));
            }
        }
        context.restore();
    }

    function renderTransform() {
        stage.style.transform = 'none';
        drawBoard();
    }

    function reset() {
        const bounds = viewport.getBoundingClientRect();
        const initialView = getInitialView(bounds.width, bounds.height);
        scale = initialView.scale;
        offsetX = initialView.offsetX;
        offsetY = initialView.offsetY;
        renderTransform();
    }

    viewport.addEventListener('pointerdown', function (event) {
        dragging = true;
        pointerX = event.clientX;
        pointerY = event.clientY;
        viewport.classList.add('is-dragging');
        viewport.setPointerCapture(event.pointerId);
    });

    viewport.addEventListener('pointermove', function (event) {
        if (!dragging) {
            return;
        }
        offsetX += event.clientX - pointerX;
        offsetY += event.clientY - pointerY;
        pointerX = event.clientX;
        pointerY = event.clientY;
        renderTransform();
    });

    function stopDragging(event) {
        dragging = false;
        viewport.classList.remove('is-dragging');
        if (event && viewport.hasPointerCapture(event.pointerId)) {
            viewport.releasePointerCapture(event.pointerId);
        }
    }

    viewport.addEventListener('pointerup', stopDragging);
    viewport.addEventListener('pointercancel', stopDragging);

    viewport.addEventListener('wheel', function (event) {
        event.preventDefault();
        const bounds = viewport.getBoundingClientRect();
        const pointerOffsetX = event.clientX - (bounds.left + bounds.width / 2);
        const pointerOffsetY = event.clientY - (bounds.top + bounds.height / 2);
        const oldScale = scale;
        const baseCellSize = Math.min(bounds.width, bounds.height) * 0.78 / board.side;
        const maxScale = Math.max(1, Math.min(bounds.width, bounds.height) / (10 * baseCellSize));
        scale = Math.max(0.35, Math.min(maxScale, scale * (event.deltaY < 0 ? 1.12 : 0.89)));
        const ratio = scale / oldScale;
        offsetX = pointerOffsetX - (pointerOffsetX - offsetX) * ratio;
        offsetY = pointerOffsetY - (pointerOffsetY - offsetY) * ratio;
        renderTransform();
    }, { passive: false });

    resetButton.addEventListener('click', reset);

    if (visualisationForm && largeBoardWarning) {
        visualisationForm.addEventListener('submit', function (event) {
            const boardSide = Number.parseInt(visualisationForm.querySelector('[name="boardSide"]').value, 10);
            const submitter = event.submitter;
            const isDesktopAction = submitter && submitter.hasAttribute('data-desktop-action');
            const isLargeBoardAllowed = submitter && submitter.hasAttribute('data-allow-large-board');

            if (boardSide > 500 && !isDesktopAction && !isLargeBoardAllowed) {
                event.preventDefault();
                largeBoardWarning.hidden = false;
                largeBoardWarning.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }
        });
    }

    window.addEventListener('resize', function () {
        reset();
    });
    drawBoard();
    reset();

    const sequenceList = document.querySelector('.sequence-list');
    const addPieceButton = document.getElementById('add-piece');
    if (sequenceList && addPieceButton) {
        const refreshRows = () => {
            [...sequenceList.children].forEach((row, index) => {
                row.querySelector('.slot-number').textContent = index + 1;
                row.querySelectorAll('select').forEach(select => {
                    select.name = select.name.replace(/sequence\[\d+\]/, `sequence[${index}]`);
                    select.id = select.id.replace(/sequence\d+/, `sequence${index}`);
                });
            });
            sequenceList.querySelectorAll('.remove-piece').forEach(button => {
                button.disabled = sequenceList.children.length === 1;
                button.onclick = () => {
                    if (sequenceList.children.length > 1) {
                        button.closest('.sequence-row').remove();
                        refreshRows();
                    }
                };
            });
        };
        addPieceButton.addEventListener('click', () => {
            const source = sequenceList.lastElementChild.cloneNode(true);
            source.querySelectorAll('select').forEach(select => select.selectedIndex = 0);
            sequenceList.appendChild(source);
            refreshRows();
        });
        refreshRows();
    }
}());
