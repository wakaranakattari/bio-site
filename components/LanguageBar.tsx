interface LanguageBarProps {
  languages: Record<string, number>;
}

export function LanguageBar({ languages }: LanguageBarProps) {
  const total = Object.values(languages).reduce((a, b) => a + b, 0);

  if (total === 0) return null;

  const languageColors: Record<string, string> = {
    TypeScript: '#3178c6',
    JavaScript: '#f1e05a',
    Python: '#3572A5',
    HTML: '#e34c26',
    CSS: '#563d7c',
    Java: '#b07219',
    Rust: '#dea584',
    Go: '#00ADD8',
    'C++': '#f34b7d',
    'C#': '#178600',
    Vue: '#41b883',
    Ruby: '#701516',
    PHP: '#4F5D95',
    Swift: '#ffac45',
    Kotlin: '#A97BFF',
  };

  const sortedLanguages = Object.entries(languages)
    .sort(([, a], [, b]) => b - a)
    .slice(0, 3);

  return (
    <div className="space-y-2">
      <div className="flex flex-wrap gap-2">
        {sortedLanguages.map(([lang, value]) => {
          const percentage = ((value / total) * 100).toFixed(1);
          return (
            <div key={lang} className="flex items-center gap-1">
              <div
                className="w-2 h-2 rounded-full"
                style={{ backgroundColor: languageColors[lang] || '#7aa2f7' }}
              />
              <span className="text-xs font-mono">{lang}</span>
              <span className="text-xs text-[#343b58]/60 dark:text-[#c0caf5]/60">
                {percentage}%
              </span>
            </div>
          );
        })}
      </div>
      <div className="h-1.5 border border-white/10 dark:border-black/10 rounded-full overflow-hidden">
        <div className="flex h-full">
          {sortedLanguages.map(([lang, value]) => {
            const percentage = (value / total) * 100;
            return (
              <div
                key={lang}
                style={{
                  width: `${percentage}%`,
                  backgroundColor: languageColors[lang] || '#7aa2f7',
                }}
                className="h-full"
              />
            );
          })}
        </div>
      </div>
    </div>
  );
}
