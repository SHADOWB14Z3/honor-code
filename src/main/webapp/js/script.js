document.addEventListener('DOMContentLoaded', function () {
    const waveCanvas = document.getElementById('landingWaveCanvas');
    if (waveCanvas) {
        const context = waveCanvas.getContext('2d');
        if (!context) {
            throw new Error('Unable to initialize the landing page background canvas.');
        }

        const reduceMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
        const waveColors = [
            'rgba(76, 91, 255, 0.28)',
            'rgba(73, 127, 255, 0.22)',
            'rgba(113, 91, 255, 0.20)',
            'rgba(43, 173, 232, 0.14)',
            'rgba(143, 97, 255, 0.16)'
        ];
        let width = 0;
        let height = 0;
        let animationFrame = 0;
        let elapsed = 0;

        const resizeCanvas = function () {
            const pixelRatio = Math.min(window.devicePixelRatio || 1, 2);
            width = window.innerWidth;
            height = window.innerHeight;
            waveCanvas.width = Math.round(width * pixelRatio);
            waveCanvas.height = Math.round(height * pixelRatio);
            context.setTransform(pixelRatio, 0, 0, pixelRatio, 0, 0);
        };

        const drawWaves = function () {
            context.clearRect(0, 0, width, height);
            context.fillStyle = '#0d0d1b';
            context.fillRect(0, 0, width, height);

            for (let wave = 0; wave < waveColors.length; wave += 1) {
                context.beginPath();
                context.moveTo(0, height * 0.49);

                for (let x = 0; x <= width; x += 5) {
                    const broadWave = Math.sin(x / 210 + elapsed + wave * 0.72) * 34;
                    const fineWave = Math.sin(x / 92 - elapsed * 0.7 + wave * 0.45) * 13;
                    const slowDrift = Math.sin(x / 480 + elapsed * 0.4 + wave) * 18;
                    const y = height * 0.49 + broadWave + fineWave + slowDrift + (wave - 2) * 11;
                    context.lineTo(x, y);
                }

                context.lineWidth = 2.5;
                context.strokeStyle = waveColors[wave];
                context.shadowColor = waveColors[wave];
                context.shadowBlur = 18;
                context.stroke();
            }

            context.shadowBlur = 0;
            if (!reduceMotion) {
                elapsed += 0.006;
                animationFrame = window.requestAnimationFrame(drawWaves);
            }
        };

        resizeCanvas();
        drawWaves();
        window.addEventListener('resize', function () {
            resizeCanvas();
            if (reduceMotion) {
                drawWaves();
            }
        });
        window.addEventListener('pagehide', function () {
            window.cancelAnimationFrame(animationFrame);
        }, { once: true });
    }

    const form = document.getElementById('assignmentForm');
    if (form) {
        form.addEventListener('submit', function () {
            const dueDate = document.getElementById('dueDate');
            if (dueDate && dueDate.value) {
                console.log('Assignment due date selected: ' + dueDate.value);
            }
        });
    }

    const assessmentForm = document.querySelector('[data-assessment-form]');
    const timer = document.getElementById('assessmentTimer');
    const timeNotice = document.getElementById('assessmentTimeNotice');

    if (assessmentForm && timer && timeNotice) {
        let secondsRemaining = 25 * 60;

        const updateTimer = function () {
            const minutes = Math.floor(secondsRemaining / 60);
            const seconds = secondsRemaining % 60;
            timer.textContent = String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');

            if (secondsRemaining <= 60) {
                timer.classList.add('timer-warning');
            }

            if (secondsRemaining === 0) {
                clearInterval(intervalId);
                timeNotice.textContent = 'Time is up. Your assessment is being submitted.';
                assessmentForm.closest('form').requestSubmit();
                return;
            }

            secondsRemaining -= 1;
        };

        updateTimer();
        const intervalId = window.setInterval(updateTimer, 1000);
    }
});
