'use client';

import { Globe, Check } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';
import { useLanguage } from './LanguageProvider';
import { useState, useRef, useEffect } from 'react';

const languages = [
  { code: 'en' as const, name: 'EN', fullName: 'English' },
  { code: 'ru' as const, name: 'RU', fullName: 'Русский' },
  { code: 'zh' as const, name: '中文', fullName: '中文' },
];

type Locale = typeof languages[number]['code'];

export function LanguageSwitcher() {
  const { locale, setLocale } = useLanguage();
  const [isOpen, setIsOpen] = useState(false);
  const buttonRef = useRef<HTMLButtonElement>(null);
  const menuRef = useRef<HTMLDivElement>(null);
  const [position, setPosition] = useState({ top: 0, left: 0 });

  const currentLang = languages.find((l) => l.code === locale);

  useEffect(() => {
    if (!isOpen || !buttonRef.current) return;

    const rect = buttonRef.current.getBoundingClientRect();
    const menuWidth = 192; // w-48
    const menuHeight = 220;
    const padding = 8;

    let left = rect.right - menuWidth;
    let top = rect.bottom + padding;

    if (left + menuWidth > window.innerWidth - padding) {
      left = window.innerWidth - menuWidth - padding;
    }
    if (left < padding) {
      left = padding;
    }
    if (top + menuHeight > window.innerHeight - padding) {
      top = rect.top - menuHeight - padding;
    }
    if (top < padding) {
      top = padding;
    }

    setPosition({ top, left });
  }, [isOpen]);

  useEffect(() => {
    function handleClick(e: MouseEvent | TouchEvent) {
      if (
        buttonRef.current &&
        menuRef.current &&
        !buttonRef.current.contains(e.target as Node) &&
        !menuRef.current.contains(e.target as Node)
      ) {
        setIsOpen(false);
      }
    }
    if (isOpen) {
      document.addEventListener('mousedown', handleClick);
      document.addEventListener('touchstart', handleClick);
    }
    return () => {
      document.removeEventListener('mousedown', handleClick);
      document.removeEventListener('touchstart', handleClick);
    };
  }, [isOpen]);

  const handleSelect = (code: Locale) => {
    setLocale(code);
    setIsOpen(false);
  };

  return (
    <>
      <motion.button
        ref={buttonRef}
        type="button"
        whileTap={{ scale: 0.96 }}
        onClick={() => setIsOpen((prev) => !prev)}
        className="flex items-center gap-2 px-4 py-2.5 rounded-lg card hover:bg-muted transition-all"
      >
        <Globe className="w-4 h-4" />
        <span className="font-medium hidden md:inline">
          {currentLang?.name ?? 'EN'}
        </span>
        <span className="font-medium md:hidden">
          {currentLang?.fullName ?? 'English'}
        </span>
      </motion.button>

      <AnimatePresence>
        {isOpen && (
          <motion.div
            ref={menuRef}
            initial={{ opacity: 0, scale: 0.98 }}
            animate={{ opacity: 1, scale: 1 }}
            exit={{ opacity: 0, scale: 0.98 }}
            transition={{ duration: 0.15 }}
            style={{
              position: 'fixed',
              top: position.top,
              left: position.left,
              zIndex: 9999,
            }}
            className="w-48 rounded-lg card shadow-xl p-2 bg-background"
          >
            {languages.map((lang) => {
              const active = locale === lang.code;

              return (
                <button
                  key={lang.code}
                  type="button"
                  onClick={() => handleSelect(lang.code)}
                  className={`w-full text-left px-4 py-3 rounded-lg flex items-center justify-between transition ${
                    active
                      ? 'bg-accent text-accent-foreground'
                      : 'hover:bg-muted'
                  }`}
                >
                  <span>{lang.fullName}</span>
                  {active && <Check className="w-4 h-4" />}
                </button>
              );
            })}
          </motion.div>
        )}
      </AnimatePresence>
    </>
  );
}