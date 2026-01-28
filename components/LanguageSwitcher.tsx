'use client';

import { Globe, Check } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';
import { useLanguage } from './LanguageProvider';
import { useState } from 'react';

const languages = [
  { code: 'es' as const, name: 'ES', fullName: 'Español' },
  { code: 'en' as const, name: 'EN', fullName: 'English' },
  { code: 'ru' as const, name: 'RU', fullName: 'Русский' },
  { code: 'zh' as const, name: '中文', fullName: '中文' },
];

export function LanguageSwitcher() {
  const { locale, setLocale } = useLanguage();
  const [isOpen, setIsOpen] = useState(false);
  const currentLang = languages.find((l) => l.code === locale);

  return (
    <div className="relative">
      <motion.button
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.95 }}
        onClick={() => setIsOpen(!isOpen)}
        className="flex items-center gap-2 px-4 py-2.5 rounded-lg card hover:bg-muted transition-all duration-300"
      >
        <Globe className="w-4 h-4" />
        <span className="font-medium hidden md:inline">{currentLang?.name}</span>
        <span className="font-medium md:hidden">{currentLang?.fullName}</span>
      </motion.button>

      <AnimatePresence>
        {isOpen && (
          <motion.div
            initial={{ opacity: 0, y: 10, scale: 0.95 }}
            animate={{ opacity: 1, y: 0, scale: 1 }}
            exit={{ opacity: 0, y: 10, scale: 0.95 }}
            className="absolute top-full right-0 mt-2 w-48 card shadow-xl p-2 z-50"
            onClick={() => setIsOpen(false)}
          >
            {languages.map((lang) => (
              <button
                key={lang.code}
                onClick={() => setLocale(lang.code)}
                className={`w-full text-left px-4 py-3 rounded-lg transition-all duration-200 flex items-center justify-between ${
                  locale === lang.code ? 'bg-accent text-accent-foreground' : 'hover:bg-muted'
                }`}
              >
                <span className="font-medium">{lang.fullName}</span>
                {locale === lang.code && <Check className="w-4 h-4" />}
              </button>
            ))}
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}
