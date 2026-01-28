'use client';

import Link from 'next/link';
import { ArrowRight, Sparkles, Code2, Cpu, User } from 'lucide-react';
import { motion } from 'framer-motion';
import { useLanguage } from './LanguageProvider';

export function Hero() {
  const { t } = useLanguage();

  return (
    <section className="min-h-screen flex items-center relative">
      <div className="container mx-auto px-4 relative z-10">
        <div className="max-w-6xl mx-auto">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
            className="mb-12"
          >
            <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full card mb-8">
              <Sparkles className="w-4 h-4 text-accent" />
              <span className="text-sm font-medium">{t('hero.role')}</span>
            </div>

            <div className="mb-8">
              <h2 className="text-xl text-muted-foreground mb-2">{t('hero.greeting')}</h2>
              <h1 className="text-5xl md:text-7xl font-bold font-mono mb-2">
                <span className="gradient-text">{t('hero.name')}</span>
              </h1>
              <p className="text-lg text-muted-foreground mb-4">{t('hero.age')}</p>
            </div>

            <p className="text-xl md:text-2xl mb-12 max-w-2xl text-muted-foreground leading-relaxed">
              {t('hero.description')}
            </p>

            <div className="flex flex-wrap gap-4 items-center">
              <Link
                href="/projects"
                className="flex items-center gap-3 px-10 py-5 rounded-2xl font-mono font-bold
                           bg-accent text-accent-foreground
                           transition-all duration-300
                           hover:translate-y-[-2px] hover:shadow-lg hover:bg-accent/90"
              >
                {t('hero.viewProjects')}
                <ArrowRight className="w-5 h-5" />
              </Link>

              <Link
                href="/contact"
                className="px-10 py-5 rounded-2xl font-mono font-bold
                           border border-accent text-accent
                           bg-transparent
                           transition-all duration-300
                           hover:translate-y-[-2px] hover:shadow-lg hover:bg-accent/10 dark:hover:bg-accent/20"
              >
                {t('hero.contact')}
              </Link>
            </div>
          </motion.div>

          <motion.div
            initial={{ opacity: 0, y: 40 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.3, duration: 0.5 }}
            className="grid md:grid-cols-3 gap-6 mt-20"
          >
            <div className="p-6 rounded-2xl card">
              <div className="flex items-center gap-4 mb-4">
                <div className="p-3 rounded-xl bg-blue-500/10">
                  <Code2 className="w-6 h-6 text-blue-500" />
                </div>
                <h3 className="text-xl font-bold">Frontend</h3>
              </div>
              <p className="text-muted-foreground">React, Next.js, TypeScript, Tailwind CSS</p>
            </div>

            <div className="p-6 rounded-2xl card">
              <div className="flex items-center gap-4 mb-4">
                <div className="p-3 rounded-xl bg-purple-500/10">
                  <Cpu className="w-6 h-6 text-purple-500" />
                </div>
                <h3 className="text-xl font-bold">Backend</h3>
              </div>
              <p className="text-muted-foreground">Node.js, Python, PostgreSQL, Docker</p>
            </div>

            <div className="p-6 rounded-2xl card">
              <div className="flex items-center gap-4 mb-4">
                <div className="p-3 rounded-xl bg-green-500/10">
                  <User className="w-6 h-6 text-green-500" />
                </div>
                <h3 className="text-xl font-bold">Community</h3>
              </div>
              <p className="text-muted-foreground">Open source contributor & tech enthusiast</p>
            </div>
          </motion.div>
        </div>
      </div>
    </section>
  );
}
