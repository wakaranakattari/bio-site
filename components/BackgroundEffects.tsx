'use client';

export function BackgroundEffects() {
  return (
    <div className="fixed inset-0 -z-10 overflow-hidden pointer-events-none">
      <div
        className="absolute inset-0 transition-all duration-500
        bg-[#f1f5f9]
        dark:bg-gradient-to-br dark:from-[#1a1b26] dark:via-[#24283b] dark:to-[#1a1b26]"
      />

      <div className="absolute inset-0">
        <div
          className="absolute top-1/4 left-1/4 w-[520px] h-[520px] rounded-full blur-[120px]
          bg-[#2959aa]/20
          dark:bg-[#7aa2f7]/10"
        />
        <div
          className="absolute bottom-1/4 right-1/4 w-[600px] h-[600px] rounded-full blur-[140px]
          bg-[#475569]/20
          dark:bg-[#bb9af7]/10"
        />
      </div>

      <div className="absolute inset-0">
        <svg className="w-full h-full text-foreground opacity-[0.14] dark:opacity-[0.05]">
          <defs>
            <pattern id="grid" width="40" height="40" patternUnits="userSpaceOnUse">
              <path d="M 40 0 L 0 0 0 40" fill="none" stroke="currentColor" strokeWidth="0.5" />
            </pattern>
          </defs>
          <rect width="100%" height="100%" fill="url(#grid)" />
        </svg>
      </div>
    </div>
  );
}
